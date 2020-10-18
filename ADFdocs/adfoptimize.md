# Azure Data Factory Performance (ADF)

Pipeline exectuion for a ETL is complex and what happen's when complex flow goes on. Here we are combining pipeline activities and then also data flow as combination to build complex ETL pipeline.

There is also combination of sequential flow and also parallel flow. This is usually a common pattern since ETL some logic depends on few process to complete to consume in other activities.

The below was tested in azure datafactory to show different form using AutoResolve runtime and self hosted runtime.

- Here is the both run compared in high level

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/adfopt3.jpg "ADF")

## ADF using autointegration runtime with TTL = 0 minutes

- Let's first see the auto integration runtime which is created by azure data factory by default. So we cannot change the TTL which will be set to 0 minutes

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/adfopt1.jpg "ADF")

- Make sure the set all the linked services and data flow to auto resolve runtime. By default this would be set.
- Create all the workflow(Pipeline) with combination of ADF activities and also data flow
- Trigger the job manually and check it.
- Below is the high level flow

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/adfopt4.jpg "ADF")

- now the inside child pipeline 

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/adfopt5.jpg "ADF")


## ADF using integration runtime with TTL = 15 minutes

- Let's first create azure self hosted integration runtime. Set the TTL to 15 minutes and select which cores are upto you.

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/adfopt2.jpg "ADF")

- Make sure the set all the linked services and data flow to Azure hosted intergation runtime. By default this would be set.
- Create all the workflow(Pipeline) with combination of ADF activities and also data flow
- Trigger the job manually and check it.
- Below is the high level flow

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/adfopt6.jpg "ADF")

- now the inside child pipeline 

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/adfopt7.jpg "ADF")

## conclusion

- TTL really helps on not the first flow but the following ones
- The first data flow cluster takes between 4 to 5 minutes.
- the following runtime 1:30 t0 2 minutes
- The above is the nature of having transient compute or on demand compute
- There are other optimization that can be applied for parquet and parition etc. Upto individual use case and data model