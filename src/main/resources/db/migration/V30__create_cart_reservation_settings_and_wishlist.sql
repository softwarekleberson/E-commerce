CREATE TABLE tb_cart_reservation_settings (
    settings_id TINYINT PRIMARY KEY,
    max_duration_minutes INT NOT NULL,
    CONSTRAINT chk_cart_reservation_duration CHECK (max_duration_minutes > 0)
);

CREATE TABLE tb_wishlist_item (
    wishlist_item_id VARCHAR(36) PRIMARY KEY,
    customer_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wishlist_customer FOREIGN KEY (customer_id)
        REFERENCES tb_customer(customer_id) ON DELETE CASCADE,
    CONSTRAINT fk_wishlist_product FOREIGN KEY (product_id)
        REFERENCES tb_product(product_id) ON DELETE CASCADE,
    CONSTRAINT uk_wishlist_customer_product UNIQUE (customer_id, product_id)
);

CREATE INDEX idx_wishlist_customer_id ON tb_wishlist_item(customer_id);
