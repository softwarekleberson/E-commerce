CREATE TABLE tb_bag (
    product_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique bag identifier, inherited from the tb_product table',
    volume DECIMAL(5,2) NOT NULL COMMENT 'Total bag capacity (in liters or other unit defined by the business)',
    color VARCHAR(255) NOT NULL COMMENT 'Predominant color of the bag',

CONSTRAINT fk_bag_product
    FOREIGN KEY (product_id)
    REFERENCES tb_product(product_id)
    ON DELETE CASCADE
) COMMENT='Stores bag-specific information linked to a generic product from the tb_product table';

CREATE INDEX idx_bag_color ON tb_bag(color) COMMENT 'Index to optimize searches and filters by bag color';
CREATE INDEX idx_bag_volume ON tb_bag(volume) COMMENT 'Index to optimize searches and filters by stock market volume';
