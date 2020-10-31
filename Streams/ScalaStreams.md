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
import org.apache.spark.eventhubs
import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.sql.functions.{ explode, split }
```

- setup event hub config
- Configure to read the stream from begining.

```
# Start from beginning of stream
val eventhubsas = dbutils.secrets.get(scope = "allsecrects", key = "schregistryevethub")
```

- Create a connectionString variable and store connection string

```
import org.apache.spark.eventhubs.ConnectionStringBuilder

val connectionString = ConnectionStringBuilder()
  .setNamespaceName("acceventschema")
  .setEventHubName("schemasample")
  .setSasKeyName("adbaccess")
  .setSasKey(eventhubsas)
  .build
```

- setup the configuration for read stream

```
val eventHubsConf = EventHubsConf(connectionString)
  .setStartingPosition(EventPosition.fromStartOfStream)
  //.setStartingPosition(EventPosition.fromEndOfStream)
```

- Setup Stream now

```
val eventhubs = spark.readStream
  .format("eventhubs")
  .options(eventHubsConf.toMap)
  .load()

var streamingInputDF = 
  spark.readStream
    .format("eventhubs")
    .options(eventHubsConf.toMap)
    .load()
```

- Now parse the json propertise

```
import org.apache.spark.sql.functions._

var streamingSelectDF = 
  streamingInputDF
   .select(get_json_object(($"body").cast("string"), "$.sensor_id").alias("sensor_id"), 
           get_json_object(($"body").cast("string"), "$.sensor_temp").alias("sensor_temp"),
           get_json_object(($"body").cast("string"), "$.sensor_status").alias("sensor_status"))
```

- Display the variables

```
display(streamingSelectDF)
```

- Avro format

```
//avro
{
    "type": "record",
    "name": "AvroUser",
    "namespace": "com.azure.schemaregistry.samples",
    "fields": [
        {
            "name": "sensor_id",
            "type": "int"
        },
        {
            "name": "sensor_temp",
            "type": "int"
        },
        {
            "name": "sensor_status",
            "type": "string"
        }
    ]
}
```

- More to come