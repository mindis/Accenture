# Azure Data Factory For Each 

## Use Case

To load data sql tables as parameter. Pass that parameters into Copy pipeline and also pass as parameters into data flow to use the value. The idea here is table will have source and destination connection string and use a pipeline to reuse by changing connection 
string instead of having multiple pipeline in Azure data factory. The pipelines will align with data sources like Azure sql as source and azure sql as sink will in one common. Azure sql to blob might be another, like that.

## Pre·requisite

- Azure SQL database
- Create table tblconnstr

```
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblconnstr](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[connstr] [varchar](500) NULL,
	[inserttime] [datetime] NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblconnstr] ADD  DEFAULT (getdate()) FOR [inserttime]
GO
```
- Insert data for connection String

```
USE [bbaccdb]
GO

INSERT INTO [dbo].[tblconnstr]
           ([connstr])
     VALUES
           ('Server=tcp:svrname.database.windows.net,1433;Database=bbaccdb;User ID=sqladmin;Password=xxxxxxx;Trusted_Connection=False;Encrypt=True;Connection Timeout=30')
INSERT INTO [dbo].[tblconnstr]
           ([connstr])
     VALUES
           ('Server=tcp:svrname.database.windows.net,1433;Database=bbaccdb1;User ID=sqladmin;Password=xxxxxxx;Trusted_Connection=False;Encrypt=True;Connection Timeout=30')
INSERT INTO [dbo].[tblconnstr]
           ([connstr])
     VALUES
           ('Server=tcp:svrname.database.windows.net,1433;Database=bbaccdb2;User ID=sqladmin;Password=xxxxxxx;Trusted_Connection=False;Encrypt=True;Connection Timeout=30')
INSERT INTO [dbo].[tblconnstr]
           ([connstr])
     VALUES
           ('Server=tcp:svrname.database.windows.net,1433;Database=bbaccdb3;User ID=sqladmin;Password=xxxxxx;Trusted_Connection=False;Encrypt=True;Connection Timeout=30')
GO
```
-- create a destination table

```
/****** Object:  Table [dbo].[tblconnstr1]    Script Date: 8/4/2020 6:42:14 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblconnstr1](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[connstr] [varchar](1000) NULL,
	[inserttime] [datetime] NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblconnstr1] ADD  DEFAULT (getdate()) FOR [inserttime]
GO
```
- Now time to create Azure data factory

## Azure Data Factory Steps

- Create a New pipeline
- Drag Lookup activity
- Drag ForEach activity and connect them both.

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach1.jpg "For Each")

- For Lookup connect the Azure SQL Database with table tblconnstr. And in the query field 

```
select connstr from tblconnstr;
```

- Select query

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach2.jpg "For Each")

- Now lets configure the items section for ForEach activity
- the goal is get it to array so that inside ForEach we can reference and use them

```
@array(activity('Lookup1').output)
```

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach3.jpg "For Each")

- Now inside ForEach
- Add Set Variable to save each row value 

```
@string(item().value)
```

- Now Drag Copy activity and configure just blob container to blob container for testing

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach4.jpg "For Each")

- Add blob container as input called adfinput

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach5.jpg "For Each")

- Add blob container as output called adfoutput

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach6.jpg "For Each")

- Now COnfigure the data flow activity
- we need to add a parameter
- Create Pipeline parameter

```
@variables('connstr')
```

- Create a data flow pipeline.

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach7.jpg "For Each")

- Connect to sql source as input

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach8.jpg "For Each")

- Create a temporary variable for data flow as cstr

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach10.jpg "For Each")

- Create a derived column
- assign tmp variable

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach9.jpg "For Each")

- Assign mappings and select the temp variable to connstr as output column

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach13.jpg "For Each")

- Write the connstr column and mapp to output table tblconnstr1
- Sink as output Azure SQL database

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach14.jpg "For Each")