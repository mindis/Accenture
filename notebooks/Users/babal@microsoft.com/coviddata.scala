// Databricks notebook source
spark.conf.set(
  "fs.azure.account.key.accbbstore.blob.core.windows.net",
  "iRfUM9DFyQKhE9BXbPOlfQIgjuz3GTQ7p0i8Y23Sxwa9CRchLHTPOPQqGrpCd6eQaQ141vc5PBqdrImOwvgXqA==")

// COMMAND ----------

val data = spark.read.option("inferSchema", "true").option("header", "true").csv("wasbs://coviddata@accbbstore.blob.core.windows.net/covid_19_data.csv")

// COMMAND ----------

display(data)

// COMMAND ----------

import org.apache.spark.sql.functions
import org.apache.spark.sql.functions.broadcast
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import scala.collection.mutable.ListBuffer
import java.util.{GregorianCalendar, Date}
import java.util.Calendar

// COMMAND ----------

data.select("year", date_format(last_day($"ObservationDate"), "MM/dd/yyyy"))

// COMMAND ----------

val df1 = data.withColumn("year", date_format(to_date($"ObservationDate"), "MM/dd/yyyy"))

// COMMAND ----------

display(df1)

// COMMAND ----------

data.createOrReplaceTempView("coviddata")

// COMMAND ----------

// MAGIC %sql
// MAGIC select * from coviddata limit 10

// COMMAND ----------

// MAGIC %sql
// MAGIC select `Country/Region`, Sum(Confirmed) as Confirmed, Sum(Deaths) as Deaths, Sum(Recovered) as Recovered from coviddata group by `Country/Region` order by `Country/Region`

// COMMAND ----------

// MAGIC %sql
// MAGIC select ObservationDate, Sum(Confirmed) as Confirmed, Sum(Deaths) as Deaths, Sum(Recovered) as Recovered from coviddata group by ObservationDate order by ObservationDate

// COMMAND ----------

import org.apache.spark.sql.functions

// COMMAND ----------

// MAGIC %sql
// MAGIC select year(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y")) as year, Sum(Confirmed) as Confirmed, Sum(Deaths) as Deaths, Sum(Recovered) as Recovered from coviddata group by year(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y")) order by year(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y"))

// COMMAND ----------

// MAGIC %sql
// MAGIC select year(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y")) as year, month(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y")) as month, 
// MAGIC Sum(Confirmed) as Confirmed, Sum(Deaths) as Deaths, Sum(Recovered) as Recovered from coviddata group by year(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y")), month(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y")) order by year(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y")), month(date_format(to_date(ObservationDate, "MM/dd/yyyy"), "M/d/y"))