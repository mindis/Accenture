# Databricks notebook source
dbutils.fs.mkdirs("dbfs:/databricks/scripts/")

# COMMAND ----------

dbutils.fs.put("/databricks/scripts/adbkey.sh","""
#!/bin/bash
keystore_file="$DB_HOME/keys/jetty_ssl_driver_keystore.jks"
keystore_password="gb1gQqZ9ZIHS"

# Use the SHA256 of the JKS keystore file as a SASL authentication secret string
sasl_secret=$(sha256sum $keystore_file | cut -d' ' -f1)

spark_defaults_conf="$DB_HOME/spark/conf/spark-defaults.conf"
driver_conf="$DB_HOME/driver/conf/config.conf"


if [ ! -e $spark_defaults_conf ]; then
    touch $spark_defaults_conf
fi

if [ ! -e $driver_conf ]; then
    touch $driver_conf
fi

# Authenticate
echo "spark.authenticate true" >> $spark_defaults_conf
echo "spark.authenticate.secret $sasl_secret" >> $spark_defaults_conf

# Configure AES encryption
echo "spark.network.crypto.enabled true" >> $spark_defaults_conf
echo "spark.network.crypto.saslFallback false" >> $spark_defaults_conf

# Configure SSL
echo "spark.ssl.enabled true" >> $spark_defaults_conf
echo "spark.ssl.keyPassword $keystore_password" >> $spark_defaults_conf
echo "spark.ssl.keyStore $keystore_file" >> $spark_defaults_conf
echo "spark.ssl.keyStorePassword $keystore_password" >> $spark_defaults_conf
echo "spark.ssl.protocol TLSv1.2" >> $spark_defaults_conf
echo "spark.ssl.standalone.enabled true" >> $spark_defaults_conf
echo "spark.ssl.ui.enabled true" >> $spark_defaults_conf

head -n -1 ${DB_HOME}/driver/conf/spark-branch.conf > $driver_conf

echo "  // Authenticate">> $driver_conf

echo "  \"spark.authenticate\" = true" >> $driver_conf
echo "  \"spark.authenticate.secret\" = \"$sasl_secret\"" >> $driver_conf

echo "  // Configure AES encryption">> $driver_conf
echo "  \"spark.network.crypto.enabled\" = true" >> $driver_conf
echo "  \"spark.network.crypto.saslFallback\" = false" >> $driver_conf

echo "  // Configure SSL">> $driver_conf

echo "  \"spark.ssl.enabled\" = true" >> $driver_conf
echo "  \"spark.ssl.keyPassword\" = \"$keystore_password\"" >> $driver_conf
echo "  \"spark.ssl.keyStore\" = \"$keystore_file\"" >> $driver_conf
echo "  \"spark.ssl.keyStorePassword\" = \"$keystore_password\"" >> $driver_conf
echo "  \"spark.ssl.protocol\" = \"TLSv1.2\"" >> $driver_conf
echo "  \"spark.ssl.standalone.enabled\" = true" >> $driver_conf
echo "  \"spark.ssl.ui.enabled\" = true" >> $driver_conf
echo " }"  >> $driver_conf

mv $driver_conf ${DB_HOME}/driver/conf/spark-branch.conf""", True)

# COMMAND ----------

display(dbutils.fs.ls("dbfs:/databricks/scripts/adbkey.sh"))