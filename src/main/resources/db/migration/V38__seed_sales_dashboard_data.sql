-- Sample sales covering the 13 calendar months from November 2025 through November 2026.
INSERT INTO tb_product (
    product_id, product_type, active, name, description, price, type_coin,
    category, brand, pricing
)
VALUES (
    UUID(), 'bag', TRUE, 'Dashboard Bag', 'Sample bag for the sales dashboard',
    10.00, 'DOLAR', 'BAG', 'Dashboard Demo', 2.00
);

INSERT INTO tb_bag (product_id, volume, color)
SELECT product_id, 20.00, 'Black'
FROM tb_product
WHERE name = 'Dashboard Bag';

INSERT INTO tb_product (
    product_id, product_type, active, name, description, price, type_coin,
    category, brand, pricing
)
SELECT UUID(), 'book', TRUE, CONCAT('Dashboard Book - ', book_categories.category),
       CONCAT('Sample book for the ', book_categories.category, ' category'),
       10.00, 'DOLAR', 'BOOKS', 'Dashboard Demo', 2.00
FROM (
    SELECT 'FICTION' AS category
    UNION ALL SELECT 'NON_FICTION'
    UNION ALL SELECT 'ROMANCE'
    UNION ALL SELECT 'MYSTERY'
    UNION ALL SELECT 'FANTASY'
    UNION ALL SELECT 'SCIENCE_FICTION'
    UNION ALL SELECT 'BIOGRAPHY'
    UNION ALL SELECT 'HISTORY'
    UNION ALL SELECT 'SELF_HELP'
    UNION ALL SELECT 'HORROR'
    UNION ALL SELECT 'THRILLER'
    UNION ALL SELECT 'YOUNG_ADULT'
    UNION ALL SELECT 'CHILDREN'
    UNION ALL SELECT 'POETRY'
    UNION ALL SELECT 'COMICS'
    UNION ALL SELECT 'SATIRE'
    UNION ALL SELECT 'TRAVEL'
    UNION ALL SELECT 'CLASSIC'
) AS book_categories;

INSERT INTO tb_book (
    product_id, synopsis, page, author, edition, isbn, category_book,
    height, width, length, weight, publisher_date
)
SELECT p.product_id, CONCAT('Sample book for the ', book_categories.category, ' category'),
       200, CONCAT('Dashboard Author ', book_categories.category), 'Demo edition',
       book_categories.isbn, book_categories.category, 23.00, 15.00, 2.00, 0.50,
       '2020-01-01'
FROM (
    SELECT 'FICTION' AS category, '9780000000001' AS isbn
    UNION ALL SELECT 'NON_FICTION', '9780000000002'
    UNION ALL SELECT 'ROMANCE', '9780000000003'
    UNION ALL SELECT 'MYSTERY', '9780000000004'
    UNION ALL SELECT 'FANTASY', '9780000000005'
    UNION ALL SELECT 'SCIENCE_FICTION', '9780000000006'
    UNION ALL SELECT 'BIOGRAPHY', '9780000000007'
    UNION ALL SELECT 'HISTORY', '9780000000008'
    UNION ALL SELECT 'SELF_HELP', '9780000000009'
    UNION ALL SELECT 'HORROR', '9780000000010'
    UNION ALL SELECT 'THRILLER', '9780000000011'
    UNION ALL SELECT 'YOUNG_ADULT', '9780000000012'
    UNION ALL SELECT 'CHILDREN', '9780000000013'
    UNION ALL SELECT 'POETRY', '9780000000014'
    UNION ALL SELECT 'COMICS', '9780000000015'
    UNION ALL SELECT 'SATIRE', '9780000000016'
    UNION ALL SELECT 'TRAVEL', '9780000000017'
    UNION ALL SELECT 'CLASSIC', '9780000000018'
) AS book_categories
JOIN tb_product p ON p.name = CONCAT('Dashboard Book - ', book_categories.category);

INSERT INTO tb_product (
    product_id, product_type, active, name, description, price, type_coin,
    category, brand, pricing
)
SELECT UUID(), 'toy', TRUE, CONCAT('Dashboard Toy - ', toy_categories.category),
       CONCAT('Sample toy for the ', toy_categories.category, ' category'),
       10.00, 'DOLAR', 'TOYS', 'Dashboard Demo', 2.00
FROM (
    SELECT 'DOLL' AS category
    UNION ALL SELECT 'ACTION_FIGURE'
    UNION ALL SELECT 'PLUSH_TOY'
    UNION ALL SELECT 'VEHICLE'
    UNION ALL SELECT 'BUILDING_BLOCKS'
    UNION ALL SELECT 'PUZZLE'
    UNION ALL SELECT 'BOARD_GAME'
    UNION ALL SELECT 'CARD_GAME'
    UNION ALL SELECT 'EDUCATIONAL'
    UNION ALL SELECT 'MUSICAL'
    UNION ALL SELECT 'OUTDOOR'
    UNION ALL SELECT 'SPORTS'
    UNION ALL SELECT 'REMOTE_CONTROL'
    UNION ALL SELECT 'ELECTRONIC'
    UNION ALL SELECT 'ROLE_PLAYING'
    UNION ALL SELECT 'CONSTRUCTION'
    UNION ALL SELECT 'SCIENTIFIC'
    UNION ALL SELECT 'COLLECTIBLE'
    UNION ALL SELECT 'CREATIVE'
    UNION ALL SELECT 'BATH_TOY'
    UNION ALL SELECT 'OTHER'
) AS toy_categories;

INSERT INTO tb_toy (
    product_id, toy_type, age_range, material, number_of_pieces,
    battery_required, battery_included
)
SELECT p.product_id, toy_categories.category, 'THREE_TO_FIVE', 'Plastic', 10, FALSE, FALSE
FROM (
    SELECT 'DOLL' AS category
    UNION ALL SELECT 'ACTION_FIGURE'
    UNION ALL SELECT 'PLUSH_TOY'
    UNION ALL SELECT 'VEHICLE'
    UNION ALL SELECT 'BUILDING_BLOCKS'
    UNION ALL SELECT 'PUZZLE'
    UNION ALL SELECT 'BOARD_GAME'
    UNION ALL SELECT 'CARD_GAME'
    UNION ALL SELECT 'EDUCATIONAL'
    UNION ALL SELECT 'MUSICAL'
    UNION ALL SELECT 'OUTDOOR'
    UNION ALL SELECT 'SPORTS'
    UNION ALL SELECT 'REMOTE_CONTROL'
    UNION ALL SELECT 'ELECTRONIC'
    UNION ALL SELECT 'ROLE_PLAYING'
    UNION ALL SELECT 'CONSTRUCTION'
    UNION ALL SELECT 'SCIENTIFIC'
    UNION ALL SELECT 'COLLECTIBLE'
    UNION ALL SELECT 'CREATIVE'
    UNION ALL SELECT 'BATH_TOY'
    UNION ALL SELECT 'OTHER'
) AS toy_categories
JOIN tb_product p ON p.name = CONCAT('Dashboard Toy - ', toy_categories.category);

INSERT INTO tb_orders (
    order_id, customer_id, delivery_id, created_at, total, type_coin,
    status, delivered, shipping_cost
)
VALUES
    ('d0000000-0000-4000-8000-000000000001', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2025-11-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000002', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2025-12-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000003', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-01-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000004', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-02-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000005', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-03-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000006', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-04-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000007', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-05-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000008', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-06-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000009', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-07-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000010', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-08-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000011', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-09-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000012', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-10-15 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00),
    ('d0000000-0000-4000-8000-000000000013', '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b', NULL, '2026-11-03 12:00:00', 400.00, 'DOLAR', 'PAID', TRUE, 0.00);

INSERT INTO tb_order_items (
    order_item_id, order_id, product_id, price, quantity, subtotal,
    item_status, reservation_id, product_ref_id
)
SELECT UUID(), o.order_id, p.name, p.price, 1, p.price,
       'DELIVERED', UUID(), p.product_id
FROM tb_orders o
CROSS JOIN tb_product p
WHERE o.order_id IN (
    'd0000000-0000-4000-8000-000000000001',
    'd0000000-0000-4000-8000-000000000002',
    'd0000000-0000-4000-8000-000000000003',
    'd0000000-0000-4000-8000-000000000004',
    'd0000000-0000-4000-8000-000000000005',
    'd0000000-0000-4000-8000-000000000006',
    'd0000000-0000-4000-8000-000000000007',
    'd0000000-0000-4000-8000-000000000008',
    'd0000000-0000-4000-8000-000000000009',
    'd0000000-0000-4000-8000-000000000010',
    'd0000000-0000-4000-8000-000000000011',
    'd0000000-0000-4000-8000-000000000012',
    'd0000000-0000-4000-8000-000000000013'
)
  AND (
      p.name = 'Dashboard Bag'
      OR p.name LIKE 'Dashboard Book - %'
      OR p.name LIKE 'Dashboard Toy - %'
  );
