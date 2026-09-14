-- Pig Job 3: Category-wise Total Sales using GROUP

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

group_category = GROUP sales_data BY category;

category_sales = FOREACH group_category GENERATE 
    group AS category,
    SUM(sales_data.sales) AS total_sales;

DUMP category_sales;
