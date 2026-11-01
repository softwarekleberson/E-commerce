ALTER TABLE tb_orders
ADD COLUMN shipping_cost DECIMAL(19, 2) NULL COMMENT 'Shipping cost charged for the order';

UPDATE tb_orders o
LEFT JOIN (
    SELECT order_id, SUM(subtotal) AS items_total
    FROM tb_order_items
    GROUP BY order_id
) order_totals ON order_totals.order_id = o.order_id
SET o.shipping_cost = CASE
    WHEN COALESCE(order_totals.items_total, 0) >= 100.00 THEN 0.00
    WHEN COALESCE(order_totals.items_total, 0) >= 50.00 THEN 30.00
    ELSE 60.00
END;

ALTER TABLE tb_orders
MODIFY COLUMN shipping_cost DECIMAL(19, 2) NOT NULL;
