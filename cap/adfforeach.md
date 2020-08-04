# Aure Data Factory For Each 

## Use Case

## Pre-requistie

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

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach1.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach2.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach3.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach4.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach5.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach6.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach7.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach8.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach9.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach10.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach11.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach12.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach13.jpg "For Each")

![alt text](https://github.com/balakreshnan/Accenture/blob/master/cap/images/foreach14.jpg "For Each")