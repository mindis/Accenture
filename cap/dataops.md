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

## PaaS Services Dashboard

https://docs.microsoft.com/en-us/azure/service-health/service-health-overview

- please follow the above best practise to know if any service outages in your area.

Status page:

- https://status.azure.com/status/
- the above page will provide status of services region wise.

## Monitoring requirements

|                 | Scope           | Related Metrics/Events to Watch*  | Metric/Source Source | Monitoring Platform(s) to Use (Monitor, Log Analytics, etc.) | ServiceNow Integration Method (direct or through another tool)
| --------------- |:---------------:| ---------------------------------:| --------------------:| -----------------------------------------:|----------------------------:| --------------------:|
| Availability    | Central, shared AzDL solution | Synthetic Transactions | E2E | AppInsights | Alert to Incident |
|                 | Per application/dashboard     | Synthetic Transactions | E2E | AppInsights | Alert to Incident |
|                 | In aggregate across AzDL      | Service Health         | E2E | AppInsights | Alert to Incident |
| Performance     | Central, shared AzDL solution | Synthetic Transactions | E2E | AppInsights | Alert to Incident |
|                 | Per application/dashboard     | Synthetic Transactions | E2E | AppInsights | Alert to Incident |
|                 | In aggregate across AzDL      | Service Health         | E2E | AppInsights | Alert to Incident |
| Capacity        | Central, shared AzDL solution | Synthetic Transactions | E2E | AppInsights | Alert to Incident |
|                 | Per application/dashboard     | Synthetic Transactions | E2E | AppInsights | Alert to Incident |
|                 | In aggregate across AzDL      | Service Health         | E2E | AppInsights | Alert to Incident |
| Operational cost| Cost Management by Resource Group | Cost Management | Cost Management |         |        |
|                 |  Cost Management by Resource Group     | Cost Management | Cost Management |       |     |
|                 |  Cost Management by Resource Group     | Cost Management        | Cost Management |      |     |

## Azure Data Factory

- ActivityFailedRuns
- PipelineFailedRuns
- TriggerFailedRuns
- SSISIntegrationRuntimeStartFailed
- PipelineSucceededRuns
- Availability

- Availability for Data Factory would some like Completed jobs vs failed jobs. For Example 100 jobs ran and 10 failed so the availability is 90%. This is for Jobs operation. I believe PipelineSucceededRuns and PipelineFailedRuns can provide these details to calculate the formula.

- Uptime of Azure Datafactory service is shows in Azure Service Availbility dashboard.

- Data Flow is an area need more details.
- Capacity

## Azure Functions

- Failed Requests
- Server Response Time
- Server requests
- Avaibility
- Code errors has to be updated into application insights to build custom dashboard.
- Capacity

## Azure Databricks

- Job Latency
- Sum Task execution per host
- properties.response
- operationName
- jobs
- Availibility
- Capacity

- Availability for Data Bricks would some like Completed jobs vs failed jobs. For Example 100 jobs ran and 10 failed so the availability is 90%. This is for Jobs operation. I believe Jobs with 200 status code and Jobs with status code <> 200 can provide these details to calculate the formula.

- Databricks application code based is using application insights and push to log analytics and build a new dashboard. These are KPI's based on customer application specific and can vary based on what they are business logic's are.

## azure synapse analytics

- CPU percentage
- Data IO percentage
- Memory Percentage
- DWU percentage
- Local Temp percentage
- Queued Queries

- DWU percentage is good one to watch as more DWU usuage performance will degrade.
- Capacity

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

## Build Run Ops Team

- Create a Team of professional
- Create an Organization
- Create a Run Book
- Design and develop Run Ops Operation manual
- Provide esclation process
- Automate support related issues