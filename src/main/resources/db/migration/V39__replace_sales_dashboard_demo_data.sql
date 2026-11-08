-- Replace the initial one-order-per-month dashboard sample with a varied
-- 13-month dataset. November 2026 contains sales through November 5 only.
DELETE oi
FROM tb_order_items oi
JOIN tb_orders o ON o.order_id = oi.order_id
WHERE o.order_id LIKE 'd0000000-0000-4000-8000-0000000000%';

DELETE FROM tb_orders
WHERE order_id LIKE 'd0000000-0000-4000-8000-0000000000%';

DELETE FROM tb_product
WHERE brand = 'Dashboard Demo'
  AND name LIKE 'Dashboard %';

CREATE TEMPORARY TABLE tmp_dashboard_numbers AS
SELECT ones.digit + tens.digit * 10 + hundreds.digit * 100 + thousands.digit * 1000 + 1 AS n
FROM (
    SELECT 0 AS digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
) ones
CROSS JOIN (
    SELECT 0 AS digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
) tens
CROSS JOIN (
    SELECT 0 AS digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
) hundreds
CROSS JOIN (
    SELECT 0 AS digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
) thousands;

ALTER TABLE tmp_dashboard_numbers ADD PRIMARY KEY (n);

INSERT INTO tb_customer (
    customer_id, system_client_status, cpf, full_name, birth_date, password_hash,
    gender, area_code, phone_number, phone_type, email, created_at, updated_at
)
SELECT CONCAT('e1000000-0000-4000-8000-', LPAD(n, 12, '0')),
       TRUE,
       CONCAT('DASH-', LPAD(n, 6, '0')),
       CONCAT('Cliente Demonstração ', LPAD(n, 4, '0')),
       DATE_ADD('1980-01-01', INTERVAL MOD(n * 31, 9000) DAY),
       '!dashboard-demo-account-disabled!',
       'NOT_INFORMED',
       '11',
       LPAD(n, 9, '0'),
       'MOBILE',
       CONCAT('dashboard.customer.', LPAD(n, 4, '0'), '@example.test'),
       '2025-11-01 00:00:00',
       '2025-11-01 00:00:00'
FROM tmp_dashboard_numbers
WHERE n <= 250;

CREATE TEMPORARY TABLE tmp_dashboard_catalog (
    catalog_number INT NOT NULL PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    product_type VARCHAR(16) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    subcategory VARCHAR(50),
    price DECIMAL(19, 2) NOT NULL,
    bag_volume DECIMAL(5, 2),
    bag_color VARCHAR(255)
);

INSERT INTO tmp_dashboard_catalog (
    catalog_number, product_id, product_type, product_name, category,
    subcategory, price, bag_volume, bag_color
)
SELECT n,
       UUID(),
       'bag',
       CONCAT('Dashboard Bag Model ', LPAD(n, 2, '0')),
       'BAG',
       NULL,
       ROUND(29.90 + MOD(n * 17, 150), 2),
       12.00 + MOD(n * 3, 35),
       CASE MOD(n, 6)
           WHEN 0 THEN 'Black'
           WHEN 1 THEN 'Blue'
           WHEN 2 THEN 'Red'
           WHEN 3 THEN 'Green'
           WHEN 4 THEN 'Gray'
           ELSE 'Beige'
       END
FROM tmp_dashboard_numbers
WHERE n <= 24;

INSERT INTO tmp_dashboard_catalog (
    catalog_number, product_id, product_type, product_name, category,
    subcategory, price, bag_volume, bag_color
)
SELECT books.catalog_number,
       UUID(),
       'book',
       CONCAT('Dashboard Book - ', books.category),
       'BOOKS',
       books.category,
       ROUND(18.90 + MOD(books.catalog_number * 13, 85), 2),
       NULL,
       NULL
FROM (
    SELECT 25 AS catalog_number, 'FICTION' AS category
    UNION ALL SELECT 26, 'NON_FICTION'
    UNION ALL SELECT 27, 'ROMANCE'
    UNION ALL SELECT 28, 'MYSTERY'
    UNION ALL SELECT 29, 'FANTASY'
    UNION ALL SELECT 30, 'SCIENCE_FICTION'
    UNION ALL SELECT 31, 'BIOGRAPHY'
    UNION ALL SELECT 32, 'HISTORY'
    UNION ALL SELECT 33, 'SELF_HELP'
    UNION ALL SELECT 34, 'HORROR'
    UNION ALL SELECT 35, 'THRILLER'
    UNION ALL SELECT 36, 'YOUNG_ADULT'
    UNION ALL SELECT 37, 'CHILDREN'
    UNION ALL SELECT 38, 'POETRY'
    UNION ALL SELECT 39, 'COMICS'
    UNION ALL SELECT 40, 'SATIRE'
    UNION ALL SELECT 41, 'TRAVEL'
    UNION ALL SELECT 42, 'CLASSIC'
) AS books;

INSERT INTO tmp_dashboard_catalog (
    catalog_number, product_id, product_type, product_name, category,
    subcategory, price, bag_volume, bag_color
)
SELECT toys.catalog_number,
       UUID(),
       'toy',
       CONCAT('Dashboard Toy - ', toys.category),
       'TOYS',
       toys.category,
       ROUND(24.90 + MOD(toys.catalog_number * 19, 176), 2),
       NULL,
       NULL
FROM (
    SELECT 43 AS catalog_number, 'DOLL' AS category
    UNION ALL SELECT 44, 'ACTION_FIGURE'
    UNION ALL SELECT 45, 'PLUSH_TOY'
    UNION ALL SELECT 46, 'VEHICLE'
    UNION ALL SELECT 47, 'BUILDING_BLOCKS'
    UNION ALL SELECT 48, 'PUZZLE'
    UNION ALL SELECT 49, 'BOARD_GAME'
    UNION ALL SELECT 50, 'CARD_GAME'
    UNION ALL SELECT 51, 'EDUCATIONAL'
    UNION ALL SELECT 52, 'MUSICAL'
    UNION ALL SELECT 53, 'OUTDOOR'
    UNION ALL SELECT 54, 'SPORTS'
    UNION ALL SELECT 55, 'REMOTE_CONTROL'
    UNION ALL SELECT 56, 'ELECTRONIC'
    UNION ALL SELECT 57, 'ROLE_PLAYING'
    UNION ALL SELECT 58, 'CONSTRUCTION'
    UNION ALL SELECT 59, 'SCIENTIFIC'
    UNION ALL SELECT 60, 'COLLECTIBLE'
    UNION ALL SELECT 61, 'CREATIVE'
    UNION ALL SELECT 62, 'BATH_TOY'
    UNION ALL SELECT 63, 'OTHER'
) AS toys;

INSERT INTO tb_product (
    product_id, product_type, active, name, description, price, type_coin,
    category, brand, pricing
)
SELECT product_id, product_type, TRUE, product_name,
       CONCAT('Produto demonstrativo para a categoria ', COALESCE(subcategory, category)),
       price, 'REAL', category, 'Dashboard Demo', ROUND(price * 0.55, 2)
FROM tmp_dashboard_catalog;

INSERT INTO tb_bag (product_id, volume, color)
SELECT product_id, bag_volume, bag_color
FROM tmp_dashboard_catalog
WHERE product_type = 'bag';

INSERT INTO tb_book (
    product_id, synopsis, page, author, edition, isbn, category_book,
    height, width, length, weight, publisher_date
)
SELECT product_id,
       CONCAT('Livro demonstrativo da categoria ', subcategory),
       120 + MOD(catalog_number * 17, 480),
       CONCAT('Autor Demo ', LPAD(catalog_number, 2, '0')),
       'Edição demonstrativa',
       CONCAT('9780000000', LPAD(catalog_number, 3, '0')),
       subcategory,
       23.00, 15.00, 2.00, 0.50,
       '2020-01-01'
FROM tmp_dashboard_catalog
WHERE product_type = 'book';

INSERT INTO tb_toy (
    product_id, toy_type, age_range, material, number_of_pieces,
    battery_required, battery_included
)
SELECT product_id,
       subcategory,
       CASE MOD(catalog_number, 5)
           WHEN 0 THEN 'ZERO_TO_TWO'
           WHEN 1 THEN 'THREE_TO_FIVE'
           WHEN 2 THEN 'SIX_TO_EIGHT'
           WHEN 3 THEN 'NINE_TO_TWELVE'
           ELSE 'THIRTEEN_PLUS'
       END,
       CASE MOD(catalog_number, 4)
           WHEN 0 THEN 'Plastic'
           WHEN 1 THEN 'Wood'
           WHEN 2 THEN 'Fabric'
           ELSE 'Metal'
       END,
       8 + MOD(catalog_number * 7, 80),
       subcategory IN ('REMOTE_CONTROL', 'ELECTRONIC'),
       subcategory = 'ELECTRONIC'
FROM tmp_dashboard_catalog
WHERE product_type = 'toy';

CREATE TEMPORARY TABLE tmp_dashboard_months (
    month_start DATE NOT NULL PRIMARY KEY,
    target_sales INT NOT NULL,
    first_sale_number INT NOT NULL,
    days_to_populate INT NOT NULL
);

INSERT INTO tmp_dashboard_months (month_start, target_sales, first_sale_number, days_to_populate)
VALUES
    ('2025-11-01', 160, 1, 30),
    ('2025-12-01', 230, 161, 31),
    ('2026-01-01', 190, 391, 31),
    ('2026-02-01', 260, 581, 28),
    ('2026-03-01', 310, 841, 31),
    ('2026-04-01', 280, 1151, 30),
    ('2026-05-01', 350, 1431, 31),
    ('2026-06-01', 420, 1781, 30),
    ('2026-07-01', 390, 2201, 31),
    ('2026-08-01', 520, 2591, 31),
    ('2026-09-01', 680, 3111, 30),
    ('2026-10-01', 850, 3791, 31),
    ('2026-11-01', 150, 4641, 5);

CREATE TEMPORARY TABLE tmp_dashboard_sales (
    order_id VARCHAR(36) NOT NULL PRIMARY KEY,
    sale_number INT NOT NULL,
    created_at DATETIME NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    item_count TINYINT NOT NULL
);

INSERT INTO tmp_dashboard_sales (order_id, sale_number, created_at, customer_id, item_count)
SELECT UUID(),
       months.first_sale_number + numbers.n - 1,
       TIMESTAMP(
           DATE_ADD(
               months.month_start,
               INTERVAL MOD(numbers.n * 7 + FLOOR(numbers.n / 5), months.days_to_populate) DAY
           ),
           MAKETIME(MOD(numbers.n * 13, 24), MOD(numbers.n * 29, 60), 0)
       ),
       CONCAT(
           'e1000000-0000-4000-8000-',
           LPAD(MOD((months.first_sale_number + numbers.n - 2) * 37, 250) + 1, 12, '0')
       ),
       1 + MOD((months.first_sale_number + numbers.n - 2) * 7, 3)
FROM tmp_dashboard_months months
JOIN tmp_dashboard_numbers numbers ON numbers.n <= months.target_sales;

INSERT INTO tb_orders (
    order_id, customer_id, delivery_id, created_at, total, type_coin,
    status, delivered, shipping_cost
)
SELECT order_id, customer_id, NULL, created_at, 0.00, 'REAL', 'PAID',
       created_at < '2026-11-01 00:00:00', 0.00
FROM tmp_dashboard_sales;

INSERT INTO tb_order_items (
    order_item_id, order_id, product_id, price, quantity, subtotal,
    item_status, reservation_id, product_ref_id
)
SELECT UUID(),
       sales.order_id,
       catalog.product_name,
       catalog.price,
       1 + MOD(sales.sale_number + item_numbers.n, 4),
       catalog.price * (1 + MOD(sales.sale_number + item_numbers.n, 4)),
       CASE
           WHEN sales.created_at < '2026-11-01 00:00:00' THEN 'DELIVERED'
           ELSE 'SEPARATING'
       END,
       UUID(),
       catalog.product_id
FROM tmp_dashboard_sales sales
JOIN tmp_dashboard_numbers item_numbers ON item_numbers.n <= sales.item_count
JOIN tmp_dashboard_catalog catalog
    ON catalog.catalog_number = MOD(sales.sale_number * 17 + item_numbers.n * 29, 63) + 1;

UPDATE tb_orders orders
JOIN (
    SELECT items.order_id, SUM(items.subtotal) AS items_total
    FROM tb_order_items items
    JOIN tmp_dashboard_sales sales ON sales.order_id = items.order_id
    GROUP BY items.order_id
) totals ON totals.order_id = orders.order_id
SET orders.shipping_cost = CASE
        WHEN totals.items_total >= 100.00 THEN 0.00
        WHEN totals.items_total >= 50.00 THEN 19.90
        ELSE 29.90
    END,
    orders.total = totals.items_total + CASE
        WHEN totals.items_total >= 100.00 THEN 0.00
        WHEN totals.items_total >= 50.00 THEN 19.90
        ELSE 29.90
    END;

DROP TEMPORARY TABLE tmp_dashboard_sales;
DROP TEMPORARY TABLE tmp_dashboard_months;
DROP TEMPORARY TABLE tmp_dashboard_catalog;
DROP TEMPORARY TABLE tmp_dashboard_numbers;
