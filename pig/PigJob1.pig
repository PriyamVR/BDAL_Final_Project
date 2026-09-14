-- Pig Job 1: Load Dataset and Display Selected Fields

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

selected_data = FOREACH sales_data GENERATE
    category,
    product_name,
    region,
    segment,
    sales,
    profit;

DUMP selected_data;
