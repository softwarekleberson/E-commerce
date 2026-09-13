CREATE TABLE tb_cart (
    cart_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for this shopping cart',
    customer_id VARCHAR(36) NOT NULL COMMENT 'Identifier of the customer who owns the cart',
    created_at TIMESTAMP NOT NULL COMMENT 'Date and time when the cart was created',
    updated_at TIMESTAMP NULL COMMENT 'Date and time when the cart was last updated',
    total_price DECIMAL(19, 2) NOT NULL COMMENT 'Total price of all items in the cart',
    coin VARCHAR(20) NOT NULL COMMENT 'Currency type for the prices (e.g., USD, BRL)',

    CONSTRAINT fk_cart_tb_customer 
        FOREIGN KEY (customer_id) REFERENCES tb_customer(customer_id) ON DELETE CASCADE
) COMMENT = 'Table that stores shopping cart data';

CREATE INDEX idx_cart_customer_id ON tb_cart(customer_id);
