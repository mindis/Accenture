# Azure Modern Data Platform Data Ops

## Use Case

Ability to Monitor Azure Modern data lake/ Data warehouse architecture as Data Operations (DataOps). Knowing what happens every day in data ingestion, processing and and storage and it's delivery to their customer helps the operations to provide SLA to their customers.

Proper reporting and alerting when job fails in any steps or processing or data point when data moves is crucial.

## Architecture

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/referencearchitecture.jpg "Architecture 1")

## Solution - Log Analytics

Use log analytics to get all logs and then build one dashboard for operations. Also build multiple dashbaord for individual services

## Components

- Azure Data Factory
- Azure Function
- Azure Data lake store
- Azure Data Bricks
- Azure Data Bricks Delta
- Azure Data Factory
- Azure Data Factory - DataFlow
- Azure Synapse Analytics workspace
- Azure Machine Learning workspace
- Power BI
- Azure DevOps
- Azure KeyVault
- Application Insights

## Azure Data Factory

- ActivityFailedRuns
- PipelineFailedRuns
- TriggerFailedRuns
- SSISIntegrationRuntimeStartFailed
- PipelineSucceededRuns
- Availability

- Availability for Data Factory would some like Completed jobs vs failed jobs. For Example 100 jobs ran and 10 failed so the availability is 90%. This is for Jobs operation. I believe PipelineSucceededRuns and PipelineFailedRuns can provide these details to calculate the formula.

- Uptime of Azure Datafactory service is shows in Azure Service Availbility dashboard.

## Azure Functions

- Failed Requests
- Server Response Time
- Server requests
- Avaibility

## Azure Databricks

- Job Latency
- Sum Task execution per host
- properties.response
- operationName
- jobs
- Availibility

- Availability for Data Bricks would some like Completed jobs vs failed jobs. For Example 100 jobs ran and 10 failed so the availability is 90%. This is for Jobs operation. I believe Jobs with 200 status code and Jobs with status code <> 200 can provide these details to calculate the formula.

## azure synapse analytics

- CPU percentage
- Data IO percentage
- Memory Percentage
- DWU percentage
- Local Temp percentage
- Queued Queries

- DWU percentage is good one to watch as more DWU usuage performance will degrade.

## Log Analytics

```
DatabricksClusters
| limit 100 

DatabricksNotebook
| limit 1000

ADFPipelineRun
| limit 100

AmlComputeClusterEvent
| limit 1000

Alert
| limit 1000

AzureDiagnostics
| limit 1000

AzureDiagnostics
| where ResourceProvider == "MICROSOFT.SQL"
| limit 1000

Usage
| limit 1000

Usage
| summarize sum(Quantity) by DataType
| limit 1000

AzureMetrics
| limit 1000

AzureMetrics
| where ResourceProvider == "MICROSOFT.SQL"
| summarize avg(Average) by MetricName

DatabricksAccounts
| limit 1000

ADFActivityRun
| limit 100
```