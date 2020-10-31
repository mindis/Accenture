# Use Structured stream to process Event hub messages

## Event hub messages with base 64 encoded message.

## use case

- Iot Hub and Event Hub uses Avro format and store message with base 64 encoded message
- Parse the data using structed streaming
- Parse the body column which is base 64 encoded

## Pre requistie

- Azure subscription
- Create a Event hub name space
- Select Standard since schema registry is not available in basic
- create a event hub with 1 parition
- create a consumer group called sample1
- Create Azure Databricks workspace
- Create a Event hub cluster
- Install event hub library jar from Maven: com.microsoft.azure:azure-eventhubs-spark_2.12:2.3.17

## Simulator to create and send data to event hub

- https://eventhubdatagenerator.azurewebsites.net/
- Copy the Event hub connection string
- Copy the name of event hub and paste it here
- leave the JSON message as like how it is
- change the number of messages to 500
- Click submit
- wait for few seconds to load the data

## Azure databricks code

- let's import necessary

```
from azure.schemaregistry import SchemaRegistryClient
from pyspark.sql.types import *
from pyspark.sql.functions import * 
from pyspark.sql.functions import unbase64,base64
from pyspark.sql.functions import *
from pyspark.sql.types import *
import json
```

- setup event hub config
- Configure to read the stream from begining.

```
# Start from beginning of stream
startOffset = "-1"

# End at the current time. This datetime formatting creates the correct string format from a python datetime object
#endTime = dt.now().strftime("%Y-%m-%dT%H:%M:%S.%fZ")


# Create the positions
startingEventPosition = {
  "offset": startOffset,  
  "seqNo": -1,            #not in use
  "enqueuedTime": None,   #not in use
  "isInclusive": True
}
```

- Create a connectionString variable and store connection string

```
connectionString = "Endpoint=sb://xxxxxx.servicebus.windows.net/;SharedAccessKeyName=adbaccess;SharedAccessKey=xxxxxxx;EntityPath=eventhubname"
```

- setup the configuration for read stream

```
conf = {}
conf['eventhubs.connectionString'] = sc._jvm.org.apache.spark.eventhubs.EventHubsUtils.encrypt(connectionString)
conf["eventhubs.consumerGroup"] = "sample1"
conf["eventhubs.startingPosition"] = json.dumps(startingEventPosition)
```

- Setup Stream now

```
df = spark \
  .readStream \
  .format("eventhubs") \
  .options(**conf) \
  .load()
```

- create a new column to parse base 64 column

```
df = df.withColumn("body", df["body"].cast("string"))
```

- Now parse the json propertise

```
df1 = df.select(get_json_object(df['body'],"$.sensor_id").alias('sensor_id'),
               get_json_object(df['body'],"$.sensor_temp").alias('sensor_temp'),
               get_json_object(df['body'],"$.sensor_status").alias('sensor_status')
               )
```

- Display the variables

```
display(df1)
```