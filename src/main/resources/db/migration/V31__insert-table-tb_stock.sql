-- Inserção na tb_stock
INSERT INTO tb_stock (
    stock_id,
    product_id,
    total_quantity,
    quantity_available
) VALUES (
    '0d22da8f-d0a1-4f61-99e2-cc97180e63b2',
    'cf6011b2-e36b-478a-b177-eddb6470600d',
    100,
    100
);

-- Inserção na tb_stock_input
INSERT INTO tb_stock_input (
    stock_input_id,
    stock_id,
    quantity,
    product_quality,
    entry_time,
    purchase_price,
    coin,
    supplier
) VALUES (
    'f9a3a71e-45d6-48e6-a632-b68e0f67e827',
    '0d22da8f-d0a1-4f61-99e2-cc97180e63b2',
    100,
    'NEW',
    '2026-10-06 19:08:53',
    15.00,
    'DOLAR',
    'Tec Lider'
);