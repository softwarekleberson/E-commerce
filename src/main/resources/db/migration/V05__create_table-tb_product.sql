CREATE TABLE tb_product (
    product_id VARCHAR(36) PRIMARY KEY COMMENT 'Public unique identifier (UUID) for the product',
    product_type VARCHAR(255) NOT NULL COMMENT 'Type of the product (e.g., Book, Toy, Electronics)',
    active BOOLEAN NOT NULL COMMENT 'Indicates if the product is active for sale',
    name VARCHAR(255) NOT NULL COMMENT 'Product name',
    description TEXT NOT NULL COMMENT 'Detailed description of the product',
    price DECIMAL(19,2) NOT NULL COMMENT 'Selling price of the product',
    type_coin VARCHAR(50) NOT NULL COMMENT 'Currency code for the price (e.g., USD, BRL)',
    category VARCHAR(50) NOT NULL COMMENT 'Category to which the product belongs',
    brand VARCHAR(255) NOT NULL COMMENT 'Brand of the product',
    pricing DECIMAL(19,2) NOT NULL COMMENT 'Purchase or production cost of the product',
    justification VARCHAR(255) COMMENT 'justification for product activation and inactivation', 
    product_status VARCHAR(50) COMMENT 'Activation or inactivation category',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp'
);

	CREATE INDEX idx_product_name ON tb_product (name);
	CREATE INDEX idx_product_category ON tb_product (category);
	CREATE INDEX idx_product_brand ON tb_product (brand);
	CREATE INDEX idx_product_price ON tb_product (price);