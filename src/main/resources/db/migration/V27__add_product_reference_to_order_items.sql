ALTER TABLE tb_order_items
    ADD COLUMN product_ref_id VARCHAR(36) NULL COMMENT 'Stable product identifier captured when the order was placed',
    ADD INDEX idx_order_items_product_ref_id (product_ref_id);
