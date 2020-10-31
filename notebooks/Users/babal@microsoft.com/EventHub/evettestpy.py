# Databricks notebook source
from azure.schemaregistry import SchemaRegistryClient
#from azure.identity import 

# COMMAND ----------

from pyspark.sql.types import *
from pyspark.sql.functions import * 
from pyspark.sql.functions import unbase64,base64

# COMMAND ----------

from azure.eventhub import EventHubConsumerClient

connection_str = 'Endpoint=sb://acceventschema.servicebus.windows.net/;SharedAccessKeyName=eventsend;SharedAccessKey=9Am9/OGh81sSChW9SqdaHFHATK+WriMAm349ilMIt80='
consumer_group = 'sample1'
eventhub_name = 'schemasample'
client = EventHubConsumerClient.from_connection_string(connection_str, consumer_group, eventhub_name=eventhub_name)
partition_ids = client.get_partition_ids()

# COMMAND ----------

import datetime 
from datetime as dt

# COMMAND ----------

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

#endingEventPosition = {
#  "offset": None,           #not in use
#  "seqNo": -1,              #not in use
#  "enqueuedTime": endTime,
#  "isInclusive": True
#}

# COMMAND ----------

from pyspark.sql.functions import *
from pyspark.sql.types import *

# COMMAND ----------

#https://eventhubdatagenerator.azurewebsites.net/

# COMMAND ----------

connectionString = "Endpoint=sb://acceventschema.servicebus.windows.net/;SharedAccessKeyName=adbaccess;SharedAccessKey=EvZJYAEl4aXiJeU5/84RkwRveaqhycNf72lruBRB9Ao=;EntityPath=schemasample"

# COMMAND ----------

import json

# COMMAND ----------

conf = {}
#conf["eventhubs.connectionString"] = connectionString
conf['eventhubs.connectionString'] = sc._jvm.org.apache.spark.eventhubs.EventHubsUtils.encrypt(connectionString)
conf["eventhubs.consumerGroup"] = "sample1"
#conf["eventhubs.startingPosition"] = "EventPosition.fromStartOfStream"
conf["eventhubs.startingPosition"] = json.dumps(startingEventPosition)

# COMMAND ----------

df = spark \
  .readStream \
  .format("eventhubs") \
  .options(**conf) \
  .load()

# COMMAND ----------

display(df)

# COMMAND ----------

df = df.withColumn("body", df["body"].cast("string"))

# COMMAND ----------

display(df)

# COMMAND ----------

from pyspark.sql.functions import *

# COMMAND ----------

df1 = df.select(get_json_object(df['body'],"$.sensor_id").alias('sensor_id'),
               get_json_object(df['body'],"$.sensor_temp").alias('sensor_temp'),
               get_json_object(df['body'],"$.sensor_status").alias('sensor_status')
               )

# COMMAND ----------

display(df1)

# COMMAND ----------



# COMMAND ----------

for s in spark.streams.active:
    s.stop()