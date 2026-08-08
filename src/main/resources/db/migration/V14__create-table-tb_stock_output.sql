CREATE TABLE tb_stock_output (
    stock_output_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for this stock output (product removal) record',
    order_id VARCHAR(36) NOT NULL COMMENT 'Identifier of the order related to this stock output',
    product_id VARCHAR(36) NOT NULL COMMENT 'Product involved in the stock output',
    quantity INT NOT NULL COMMENT 'Quantity of product removed from stock',
    stock_id VARCHAR(36) NOT NULL COMMENT 'Stock record affected by this output',

    CONSTRAINT fk_output_tb_stock 
        FOREIGN KEY (stock_id) REFERENCES tb_stock(stock_id) ON DELETE CASCADE,
    CONSTRAINT fk_output_tb_product 
        FOREIGN KEY (product_id) REFERENCES tb_product(product_id) ON DELETE CASCADE
) COMMENT = 'Table to track stock output (product removals) linked to orders';

CREATE INDEX idx_stock_output_product_id ON tb_stock_output(product_id);
CREATE INDEX idx_stock_output_stock_id ON tb_stock_output(stock_id);
