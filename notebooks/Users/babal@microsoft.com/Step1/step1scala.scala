// Databricks notebook source
sc.version

// COMMAND ----------

val df = spark.read.json("/databricks-datasets/samples/people/people.json")

// COMMAND ----------

display(df)

// COMMAND ----------

val data = spark.read.csv("/databricks-datasets/samples/population-vs-price/data_geo.csv", header="true", inferSchema="true")

// COMMAND ----------

val diamonds = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("/databricks-datasets/Rdatasets/data-001/csv/ggplot2/diamonds.csv")

// COMMAND ----------

display(diamonds)

// COMMAND ----------

// MAGIC %sh 
// MAGIC wget -P /dbfs/tmp/taxi-csv/ -i https://raw.githubusercontent.com/toddwschneider/nyc-taxi-data/master/setup_files/raw_data_urls.txt

// COMMAND ----------

// MAGIC %fs
// MAGIC ls /tmp/taxi-csv/

// COMMAND ----------

