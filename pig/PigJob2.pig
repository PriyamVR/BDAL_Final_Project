-- Pig Job 2: Filter High Sales Records

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

high_sales = FILTER sales_data BY sales > 1000;

DUMP high_sales;
