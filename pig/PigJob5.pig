-- Pig Job 5: Region-wise Average Sales Analysis

sales_data = LOAD 'superstore.csv'
USING PigStorage(',')
AS (
    row_id:int,
    order_id:chararray,
    category:chararray,
    product_name:chararray,
    region:chararray,
    segment:chararray,
    sales:double,
    profit:double,
    quantity:int
);

group_region = GROUP sales_data BY region;

region_sales = FOREACH group_region GENERATE
    group AS region,
    AVG(sales_data.sales) AS average_sales;

DUMP region_sales;
