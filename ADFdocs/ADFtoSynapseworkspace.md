# Migrate Azure data factory Jobs to Azure Synapse Analytics

## Migrate existing Azure modern data warehouse architecture to Azure Synapse Analytics - workspace

Lot of us using azure modern data platform architecture using PaaS for modernizing our data estate. 
The question i asked was will it be good, how do convert my azure data factory assets to new architecture.

- Given there is no CI/CD built as of now i re created the pipeline
- Used the same config and same components
- Used the same logic so that there is no difference
- Compared ADF json code and very much same

## Modern data warehouse/lake architecture

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/referencearchitecture.jpg "ADF")

- Below is the ADF pipeline i built
- Combination of Data flow
- Pipeline with For each and data flow
- Lookup activity
- Most data flow flatten JSON structure
- Low volume data
- Mimic real time

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/adfarch1.jpg "ADF")

- For Each pipeline sample

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/adfarch4.jpg "ADF")

## New Azure Synapse Analytics Workspace arhitecture

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/referencearchitecture-new.jpg "ADF")

- Below is the ADF pipeline i built
- Combination of Data flow
- Pipeline with For each and data flow
- Lookup activity
- Most data flow flatten JSON structure
- Low volume data
- Mimic real time

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/adfarch2.jpg "ADF")

- For Each pipeline sample

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/adfarch3.jpg "ADF")