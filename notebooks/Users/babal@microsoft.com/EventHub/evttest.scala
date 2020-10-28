// Databricks notebook source
//https://eventhubdatagenerator.azurewebsites.net/

// COMMAND ----------

import org.apache.spark.eventhubs
import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }

// COMMAND ----------

import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.sql.functions.{ explode, split }

// COMMAND ----------

val eventhubsas = dbutils.secrets.get(scope = "allsecrects", key = "schregistryevethub")

// COMMAND ----------

//https://eventhubdatagenerator.azurewebsites.net/

// COMMAND ----------

import org.apache.spark.eventhubs.ConnectionStringBuilder

val connectionString = ConnectionStringBuilder()
  .setNamespaceName("acceventschema")
  .setEventHubName("schemasample")
  .setSasKeyName("adbaccess")
  .setSasKey(eventhubsas)
  .build

// COMMAND ----------

val eventHubsConf = EventHubsConf(connectionString)
  .setStartingPosition(EventPosition.fromStartOfStream)
  //.setStartingPosition(EventPosition.fromEndOfStream)

// COMMAND ----------

val eventhubs = spark.readStream
  .format("eventhubs")
  .options(eventHubsConf.toMap)
  .load()

// COMMAND ----------

var streamingInputDF = 
  spark.readStream
    .format("eventhubs")
    .options(eventHubsConf.toMap)
    .load()

// COMMAND ----------

display(streamingInputDF)

// COMMAND ----------

import org.apache.spark.sql.functions._

var streamingSelectDF = 
  streamingInputDF
   .select(get_json_object(($"body").cast("string"), "$.sensor_id").alias("sensor_id"), 
           get_json_object(($"body").cast("string"), "$.sensor_temp").alias("sensor_temp"),
           get_json_object(($"body").cast("string"), "$.sensor_status").alias("sensor_status"))

// COMMAND ----------

display(streamingSelectDF)

// COMMAND ----------

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

// COMMAND ----------

//sample payload
//https://eventhubdatagenerator.azurewebsites.net/
{
  "sensor_id":{
    "faker": {
      "fake": "{{random.number(100)}}"
    }
  },
  "sensor_temp": {
    "type": "integer", "minimum": "18", "maximum": "100"
  },
  "sensor_status": {
    "type": "string",
    "faker": {
      "random.arrayElement": [["OK", "WARN", "FAIL"]]
    }
  }
}

// COMMAND ----------

