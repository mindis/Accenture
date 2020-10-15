# Azure Event hub - Stream processing with Azure Data Bricks

## Architecture

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/nearrealtime.jpg "Service Health")

## Create Structured Streaming

## Create Azure Keyvault

- Create a new resource for Azure Keyvault
- Give a name and location
- Leave others the default
- Create a secret called "eventhubsaskey" and save the Event hub key
- Now go to Azure databricks scope URL

```
https://adbid.azuredatabricks.net/?o=workspaceid#secrets/createScope
```

- Create a secret scope access
- Give a scope name as "allsecrects"
- Get the Keyvault URL from Keyvault overview page
- Go to Properties in Azure Keyvault page in portal
- copy the ResourceID value
- Paste here in the resoruce id section
- i left others the default

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/keyadb1.jpg "Service Health")

- then click create

![alt text](https://github.com/balakreshnan/Accenture/blob/master/images/keyadb2.jpg "Service Health")

## Azure Databricks Code

- Let create a notebook
- lets import necessary libraries
- Here is the azure event hubs library version for maven cordinates

```
com.microsoft.azure:azure-eventhubs-spark_2.12:2.3.17
```

- now import library in notebook

```
import org.apache.spark.eventhubs
import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.sql.functions.{ explode, split }
```

- let's read the event hub sas key from keyvault

```
val eventhubsas = dbutils.secrets.get(scope = "allsecrects", key = "eventhubsaskey")
```

- now build the event hub connection string

```
import org.apache.spark.eventhubs.ConnectionStringBuilder

val connectionString = ConnectionStringBuilder()
  .setNamespaceName("eventhubnamespacename")
  .setEventHubName("eventhub")
  .setSasKeyName("saskeyname")
  .setSasKey(eventhubsas)
  .build
```

- Set the connection string

```
val eventHubsConf = EventHubsConf(connectionString)
  .setStartingPosition(EventPosition.fromEndOfStream)
```

- read the Event hub stream

```
val eventhubs = spark.readStream
  .format("eventhubs")
  .options(eventHubsConf.toMap)
  .load()
```

- do some processing

```
// split lines by whitespaces and explode the array as rows of 'word'
val df = eventhubs.select(explode(split($"body".cast("string"), "\\s+")).as("word"))
  .groupBy($"word")
  .count
```

- display the stream

```
display(df.select($"word", $"count"))
```

more to come