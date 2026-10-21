-- Inserção na tb_cart
INSERT INTO tb_cart (
    cart_id,
    customer_id,
    created_at,
    updated_at,
    total_price,
    coin
) VALUES (
    'e723973c-3056-446b-b403-e6ce6f0b1bde',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    '2026-10-06 19:08:29',
    '2026-10-06 19:08:53',
    67.50,
    'DOLAR'
);


INSERT INTO tb_reservation (
    reservation_id,
    cart_id,
    customer_id,
    quantity,
    reservation_time,
    reserve_status,
    stock_id
) VALUES 
(
    '57f51bdd-f0e3-472d-870d-f0d75230b97e',
    'e723973c-3056-446b-b403-e6ce6f0b1bde',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    2,
    '2026-10-06 19:08:53',
    'ACTIVE',
    '0d22da8f-d0a1-4f61-99e2-cc97180e63b2'
),
(
    'aabc7ab1-147b-47a3-bcfa-5c59a94441a4',
    'e723973c-3056-446b-b403-e6ce6f0b1bde',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    1,
    '2026-10-06 19:08:49',
    'CANCELED',
    '0d22da8f-d0a1-4f61-99e2-cc97180e63b2'
),
(
    'ab57dd62-a200-4c28-b732-fda0ccf97135',
    'e723973c-3056-446b-b403-e6ce6f0b1bde',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    1,
    '2026-10-06 19:08:29',
    'ACTIVE',
    '0d22da8f-d0a1-4f61-99e2-cc97180e63b2'
);


-- Inserção na tb_cart_item
INSERT INTO tb_cart_item (
    cart_item_id,
    product_id,
    product_name,
    url_product,
    quantity,
    unit_price,
    coin,
    subtotal,
    reservation_id,
    cart_id
) VALUES 
(
    '6d541e06-1879-42da-94b4-7c18a575bba0',
    'cf6011b2-e36b-478a-b177-eddb6470600d',
    'Clean Code',
    'https://m.media-amazon.com/images/I/71nj3JM-igL._SY385_.jpg',
    1,
    22.50,
    'DOLAR',
    22.50,
    'ab57dd62-a200-4c28-b732-fda0ccf97135',
    'e723973c-3056-446b-b403-e6ce6f0b1bde'
),
(
    'ffa2ba96-41a5-4ea7-b3a0-120384649015',
    'cf6011b2-e36b-478a-b177-eddb6470600d',
    'Clean Code',
    'https://m.media-amazon.com/images/I/71nj3JM-igL._SY385_.jpg',
    2,
    22.50,
    'DOLAR',
    45.00,
    'ab57dd62-a200-4c28-b732-fda0ccf97135',
    'e723973c-3056-446b-b403-e6ce6f0b1bde'
);