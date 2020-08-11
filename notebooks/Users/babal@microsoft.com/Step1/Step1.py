# Databricks notebook source
print(sc.version)

# COMMAND ----------

val df = spark.read.json("/databricks-datasets/samples/people/people.json")

# COMMAND ----------

# MAGIC %sh 
# MAGIC wget -P /dbfs/tmp/taxi-csv/ -i https://raw.githubusercontent.com/toddwschneider/nyc-taxi-data/master/setup_files/raw_data_urls.txt

# COMMAND ----------

# MAGIC %fs
# MAGIC ls /tmp/taxi-csv/

# COMMAND ----------

df_green = spark.read.format("csv").option("header", "true").load("tmp/taxi-csv/green_tripdata_*.csv")