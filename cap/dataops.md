# Azure Modern Data Platform Data Ops

## Use Case

Ability to Monitor Azure Modern data lake/ Data warehouse architecture as Data Operations (DataOps). Knowing what happens every day in data ingestion, processing and and storage and it's delivery to their customer helps the operations to provide SLA to their customers.

Proper reporting and alerting when job fails in any steps or processing or data point when data moves is crucial.

## Architecture

## Logical

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/monitoropslogical.jpg "Service Health")

## Technical Architecture

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

## Azure Service Health Page in Portal

- Go to Azure Portal
- Seach for Service health
- Click the service health resource
- Select the subscription
- Select the regions where application are deployed
- Select the services for the application
- Save as and give a name for the view.

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/servicehealth.jpg "Service Health")

## Monitoring requirements

|                  | scope                             | Related Metrics/Events to Watch* | Metric/Source Source | Monitoring Platform(s) to Use (Monitor, Log Analytics, etc.) | ServiceNow Integration Method (direct or through another tool) |
|------------------|-----------------------------------|----------------------------------|----------------------|--------------------------------------------------------------|----------------------------------------------------------------|
| Availability     | Central, shared AzDL solution     | Synthentic Transcations          | E2E                  | App Insights                                                 | Alert to incidents                                             |
|                  | Per application/dashboard         | Synthentic Transcations          | E2E                  | App Insights                                                 | Alert to incidents                                             |
|                  | In aggregate across AzDL          | Service Health                   | E2E                  | App Insights                                                 | Alert to incidents                                             |
| Performance      | Central, shared AzDL solution     | Synthentic Transcations          | E2E                  | App Insights                                                 | Alert to incidents                                             |
|                  | Per application/dashboard         | Synthentic Transcations          | E2E                  | App Insights                                                 | Alert to incidents                                             |
|                  | In aggregate across AzDL          | Service Health                   | E2E                  | App Insights                                                 | Alert to incidents                                             |
| Capacity         | Central, shared AzDL solution     | Synthentic Transcations          | E2E                  | App Insights                                                 | Alert to incidents                                             |
|                  | Per application/dashboard         | Synthentic Transcations          | E2E                  | App Insights                                                 | Alert to incidents                                             |
|                  | In aggregate across AzDL          | Service Health                   | E2E                  | App Insights                                                 | Alert to incidents                                             |
| Operational Cost | Cost Management by Resource Group | Cost Management                  | Cost Management      |                                                              |                                                                |
|                  | Cost Management by Resource Group | Cost Management                  | Cost Management      |                                                              |                                                                |
|                  | Cost Management by Resource Group | Cost Management                  | Cost Management      |                                                              |                                                                |

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
-
```
ADFActivityRun 
| where  OperationName == "dataflow1 - Succeeded"
```

- Expand Output -> runStatus -> Metrics -> OutputPop

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/adfdataflow1.jpg "Data flow")

- Data flow metrics - https://docs.microsoft.com/en-us/azure/data-factory/control-flow-execute-data-flow-activity#use-data-flow-activity-results-in-a-subsequent-activity

- Capacity
- Azure Data Factory Azure Monitor - https://docs.microsoft.com/en-us/azure/data-factory/monitor-using-azure-monitor
- Azure Data Factory Alerts - https://azure.microsoft.com/en-in/blog/create-alerts-to-proactively-monitor-your-data-factory-pipelines/

## Azure Data lake store

- Azure Data lake Store Gen2 is storage engine which is storage enginer and has not way to send diagnotics logs to log analytics
- Stores logs in $logs folder
- documentation is available - https://docs.microsoft.com/en-us/azure/storage/common/storage-analytics-logging?tabs=dotnet
- Use power shell or cli to read the logs
- Can also use programming language

- Enable logging

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/storagelog1.jpg "Service Health")

- View insights in Azure Portal

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/storagelog2.jpg "Service Health")
![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/storagelog3.jpg "Service Health")
![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/storagelog4.jpg "Service Health")
![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/storagelog5.jpg "Service Health")
![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/storagelog6.jpg "Service Health")


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

- Azure Databricks monitor with Azure Monitor - https://docs.microsoft.com/en-us/azure/architecture/databricks-monitoring/
- Using Application insights - https://docs.microsoft.com/en-us/archive/msdn-magazine/2018/june/azure-databricks-monitoring-azure-databricks-jobs-with-application-insights

## Azure Synapse Analytics

- CPU percentage - Availability
- Data IO percentage
- Memory Percentage
- DWU percentage - Capacity
- Local Temp percentage
- Queued Queries

- DWU percentage is good one to watch as more DWU usuage performance will degrade (Capacity).
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

## Reference

- https://github.com/Azure-Samples/modern-data-warehouse-dataops/tree/master/e2e_samples/parking_sensors#observability--monitoring