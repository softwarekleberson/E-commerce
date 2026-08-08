CREATE TABLE tb_stock (
    stock_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique stock identifier',
    product_id VARCHAR(36) NOT NULL COMMENT 'Product ID referenced in tb_product table',
    total_quantity INT COMMENT 'Total quantity of the product in stock (including reserved)',
    quantity_available INT COMMENT 'Quantity available for sale',

    CONSTRAINT fk_stock_product 
        FOREIGN KEY (product_id) REFERENCES tb_product(product_id),

    CONSTRAINT uq_stock_product 
        UNIQUE (product_id)
) COMMENT = 'Table responsible for managing product stock levels';


