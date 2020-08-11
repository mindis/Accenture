// Databricks notebook source
sc.version

// COMMAND ----------

val df = spark.read.json("/databricks-datasets/samples/people/people.json")

// COMMAND ----------

display(df)