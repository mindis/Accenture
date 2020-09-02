# Enable node to node encryption with Azure databricks

## First get the init scripts

- Create a init scripts
- Create a another databricks cluster
- Create a scala notebook
- Write the below scripts

```
dbutils.fs.put("/databricks/scripts/set-encryption.sh","""
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

echo " // Authenticate">> $driver_conf

echo " \"spark.authenticate\" = true" >> $driver_conf
echo " \"spark.authenticate.secret\" = \"$sasl_secret\"" >> $driver_conf

echo " // Configure AES encryption">> $driver_conf
echo " \"spark.network.crypto.enabled\" = true" >> $driver_conf
echo " \"spark.network.crypto.saslFallback\" = false" >> $driver_conf

echo " // Configure SSL">> $driver_conf

echo " \"spark.ssl.enabled\" = true" >> $driver_conf
echo " \"spark.ssl.keyPassword\" = \"$keystore_password\"" >> $driver_conf
echo " \"spark.ssl.keyStore\" = \"$keystore_file\"" >> $driver_conf
echo " \"spark.ssl.keyStorePassword\" = \"$keystore_password\"" >> $driver_conf
echo " \"spark.ssl.protocol\" = \"TLSv1.2\"" >> $driver_conf
echo " \"spark.ssl.standalone.enabled\" = true" >> $driver_conf
echo " \"spark.ssl.ui.enabled\" = true" >> $driver_conf
echo " }" >> $driver_conf

mv $driver_conf ${DB_HOME}/driver/conf/spark-branch.conf

""",true)
```

- The above script will save as set-encryption.sh
- Get the path

```
display(dbutils.fs.ls("dbfs:/databricks/scripts/set-encryption.sh"))
```

## Create a new cluster

- Create a new Azure data bricks cluster
- expand the advanced options
- Specifiy the init scripts path 

```
dbfs:/databricks/scripts/set-encryption.sh
```

- Start the cluster and wait for it be running state

## Test the Cluster

- Open a notebook in the workspace section
- I use scala as programming language
- On the notebook cell

```
sc.version
```

- Now lets read some files 

```
val df = spark.read.json("/databricks-datasets/samples/people/people.json")
display(df)
```

```
val diamonds = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("/databricks-datasets/Rdatasets/data-001/csv/ggplot2/diamonds.csv")
display(diamonds)
```

- If the cells can run then the cluster is setup with encryption enabled.