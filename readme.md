# Store Demo

This is a small rest-api that finds the price with the highest priority for a given brand and product in a certain date.

## Main Problem

A brand/product combination can have more than one price in a given date. We need to figure out a way to get the price
with the highest priority in that date.

For example:

PRICES TABLE

| ID | BRAND_ID | PRODUCT_ID | PRICE_LIST_ID | START_DATE          | END_DATE            | PRIORITY | PRICE | CURR | LAST_UPDATED_DATE   | LAST_UPDATED_BY |
|----|----------|------------|---------------|---------------------|---------------------|----------|-------|------|---------------------|-----------------|
|  1 |        1 |      35455 |             1 | 2020-06-14-00.00.00 | 2020-12-31-23.59.59 |        0 |  35.5 | EUR  | 2020-06-14-00.00.00 | user1           |
|  2 |        1 |      35455 |             2 | 2020-06-14-15.00.00 | 2020-06-14-18.30.00 |        1 | 25.45 | EUR  | 2020-06-14-00.00.00 | user1           |
|  3 |        1 |      35455 |             3 | 2020-06-15-00.00.00 | 2020-06-15-11.00.00 |        1 |  30.5 | EUR  | 2020-06-14-00.00.00 | user2           |
|  4 |        1 |      35455 |             4 | 2020-06-15-16.00.00 | 2020-12-31-23.59.59 |        1 | 38.95 | EUR  | 2020-06-14-00.00.00 | user1           |

Test case 1:

- BRAND_ID = 1
- PRODUCT_ID = 35455
- DATE = 2020-06-14-10.00.00
- EXPECTED_PRICE = 35.5

The expected price is the only one in that date.

Test case 2:

- BRAND_ID = 1
- PRODUCT_ID = 35455
- DATE = 2020-06-14-16.00.00
- EXPECTED_PRICE = 25.45

The expected price is 25.45 due to the highest priority in the record with ID = 2.

### Corner Cases

We need to consider that for a brand/product combination the table can contain multiple records with the same priority.
To resolve it, we need to adopt a strategy to pick the most relevant record.

## Solution in SQL

```sql
SELECT DISTINCT p1.*
FROM PRICES p1
INNER JOIN (
	SELECT MAX(p2.PRIORITY) AS PRIORITY
	,p2.BRAND_ID
	,p2.PRODUCT_ID
	FROM PRICES p2
	WHERE p2.BRAND_ID = 1
	AND p2.PRODUCT_ID = 35455
	AND p2.START_DATE <= '2020-06-14T16:00' 
	AND p2.END_DATE >= '2020-06-14T16:00'
	GROUP BY BRAND_ID, PRODUCT_ID
) p3 ON p3.BRAND_ID = p1.BRAND_ID
AND p3.PRODUCT_ID = p1.PRODUCT_ID
AND p3.PRIORITY = p1.PRIORITY
WHERE p1.BRAND_ID = 1
AND p1.PRODUCT_ID = 35455
AND p1.START_DATE <= '2020-06-14T16:00' 
AND p1.END_DATE >= '2020-06-14T16:00'
```

## Technologies

- Java 8
- Maven 3
- Spring 2.x

## To Compile

You need to run:

```shell
mvn clean package
```

## To Test

You need to run:

```shell
mvn clean test
```

## To Run

You need to run:

```shell
java -jar store-0.1.0.jar
```
