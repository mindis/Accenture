// Databricks notebook source
import org.apache.spark.eventhubs
import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }

// COMMAND ----------

import org.apache.spark.eventhubs.ConnectionStringBuilder

val connectionString = ConnectionStringBuilder()
  .setNamespaceName("accevents")
  .setEventHubName("acceventsdevops")
  .setSasKeyName("stream")
  .setSasKey("ReUhF5nGTnpD74jF+qqPN2joC4M1w02hCv8jUjImEXE=")
  .build

// COMMAND ----------

val eventHubsConf = EventHubsConf(connectionString)
  .setStartingPosition(EventPosition.fromEndOfStream)

// COMMAND ----------

val eventhubs = spark.readStream
  .format("eventhubs")
  .options(eventHubsConf.toMap)
  .load()

// COMMAND ----------

import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.sql.functions.{ explode, split }

// COMMAND ----------

// split lines by whitespaces and explode the array as rows of 'word'
val df = eventhubs.select(explode(split($"body".cast("string"), "\\s+")).as("word"))
  .groupBy($"word")
  .count

// COMMAND ----------

// follow the word counts as it updates
display(df.select($"word", $"count"))