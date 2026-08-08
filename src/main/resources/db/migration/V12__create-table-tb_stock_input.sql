CREATE TABLE tb_stock_input (
    stock_input_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for this stock input entry',
    stock_id VARCHAR(36) NOT NULL COMMENT 'Stock record where the input will be registered',
    quantity INT UNSIGNED NOT NULL COMMENT 'Quantity of products received',
    product_quality VARCHAR(50) NOT NULL COMMENT 'Quality description of the received products',
    entry_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date and time of stock entry',
    purchase_price DECIMAL(19, 2) NOT NULL COMMENT 'Purchase price per unit',
    coin VARCHAR(50) NOT NULL COMMENT 'Type coin supplier product',
    supplier VARCHAR(255) NOT NULL COMMENT 'Supplier name (not linked to a supplier table)',

    CONSTRAINT fk_input_stock 
        FOREIGN KEY (stock_id) REFERENCES tb_stock(stock_id) ON DELETE CASCADE
        
) COMMENT = 'Table to track stock input (product entries) records';
