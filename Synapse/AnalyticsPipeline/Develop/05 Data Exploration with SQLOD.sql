SELECT
    TOP 100 *
FROM
    OPENROWSET(
        BULK 'https://accsynapsestorage.blob.core.windows.net/curateddata/ordersumamtion/OrdersByState/part-00000-e73ae662-3767-44ef-ba2e-84a26ecc7ee5-c000.snappy.parquet',
        FORMAT='PARQUET'
    ) AS [result]
