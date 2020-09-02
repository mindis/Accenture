// Databricks notebook source
sc.version

// COMMAND ----------

val df = spark.read.json("/databricks-datasets/samples/people/people.json")

// COMMAND ----------

display(df)

// COMMAND ----------

val diamonds = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("/databricks-datasets/Rdatasets/data-001/csv/ggplot2/diamonds.csv")

// COMMAND ----------

display(diamonds)

// COMMAND ----------

diamonds.columns