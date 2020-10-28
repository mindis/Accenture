// Databricks notebook source
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

// split lines by whitespaces and explode the array as rows of 'word'
val df = eventhubs.select(explode(split($"body".cast("string"), "\\s+")).as("word"))
  .groupBy($"word")
  .count

// COMMAND ----------

display(df)

// COMMAND ----------

display(df.select($"word", $"count"))