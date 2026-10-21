-- Gera 130 pedidos por mês durante 13 meses (setembro/2025 a setembro/2026).
-- Reutiliza o cliente e os produtos cadastrados nas migrations anteriores.
SET @cliente_id := '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b';
SET @produto_mochila_id := 'a0e16dd4-2cd8-465d-b79d-094e3510cdf5';
SET @produto_livro_id := 'cf6011b2-e36b-478a-b177-eddb6470600d';

DROP TEMPORARY TABLE IF EXISTS tmp_vendas;

CREATE TEMPORARY TABLE tmp_vendas AS
WITH RECURSIVE
meses AS (
    SELECT DATE('2025-09-01') AS mes
    UNION ALL
    SELECT DATE_ADD(mes, INTERVAL 1 MONTH)
    FROM meses
    WHERE mes < DATE('2026-09-01')
),
numeros AS (
    SELECT unidades.n + dezenas.n * 10 + 1 AS n
    FROM (
        SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL
        SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL
        SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
    ) AS unidades
    CROSS JOIN (
        SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL
        SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL
        SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL
        SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL
        SELECT 12
    ) AS dezenas
),
vendas AS (
    SELECT
        meses.mes,
        numeros.n,
        TIMESTAMP(
            DATE_ADD(meses.mes, INTERVAL MOD(numeros.n * 7, DAY(LAST_DAY(meses.mes))) DAY),
            MAKETIME(9 + MOD(numeros.n, 10), MOD(numeros.n * 13, 60), 0)
        ) AS data_venda,
        MOD(numeros.n, 2) AS produto
    FROM meses
    CROSS JOIN numeros
    WHERE numeros.n <= 130
)
SELECT
    UUID() AS order_id,
    @cliente_id AS customer_id,
    data_venda AS created_at,
    CASE WHEN produto = 0 THEN 16.50 ELSE 22.50 END AS valor,
    CASE WHEN produto = 0 THEN 'Mochila de treino' ELSE 'Clean Code' END AS nome_produto,
    CASE WHEN produto = 0 THEN @produto_mochila_id ELSE @produto_livro_id END AS product_ref_id
FROM vendas;

INSERT INTO tb_orders (
    order_id,
    customer_id,
    delivery_id,
    created_at,
    value,
    type_coin,
    status,
    delivered
)
SELECT
    order_id,
    customer_id,
    NULL,
    created_at,
    valor,
    'DOLAR',
    'PAID',
    1
FROM tmp_vendas;

INSERT INTO tb_order_items (
    order_item_id,
    order_id,
    product_id,
    price,
    quantity,
    subtotal,
    item_status,
    reservation_id,
    product_ref_id
)
SELECT
    UUID(),
    order_id,
    nome_produto,
    valor,
    1,
    valor,
    'DELIVERED',
    UUID(),
    product_ref_id
FROM tmp_vendas;

DROP TEMPORARY TABLE tmp_vendas;
