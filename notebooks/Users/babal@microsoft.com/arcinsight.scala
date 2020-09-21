// Databricks notebook source
spark.conf.set(
  "fs.azure.account.key.accbbstore.blob.core.windows.net",
  "iRfUM9DFyQKhE9BXbPOlfQIgjuz3GTQ7p0i8Y23Sxwa9CRchLHTPOPQqGrpCd6eQaQ141vc5PBqdrImOwvgXqA==")

// COMMAND ----------

val data = spark.read.option("multiline", "true").json("wasbs://adfinput@accbbstore.blob.core.windows.net/13456-2020.03.23.2-Sample2.json")

// COMMAND ----------

data.printSchema

// COMMAND ----------

data.take(1)

// COMMAND ----------

data.createOrReplaceTempView("devopsdata")

// COMMAND ----------

// MAGIC %sql
// MAGIC Select * from devopsdata limit 10;