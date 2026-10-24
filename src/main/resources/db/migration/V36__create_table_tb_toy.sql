CREATE TABLE tb_toy (
    product_id VARCHAR(36) PRIMARY KEY,
    toy_type VARCHAR(50) NOT NULL,
    age_range VARCHAR(30) NOT NULL,
    material VARCHAR(255) NOT NULL,
    number_of_pieces INT NULL,
    battery_required BOOLEAN NOT NULL DEFAULT FALSE,
    battery_included BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_toy_product
        FOREIGN KEY (product_id) REFERENCES tb_product(product_id) ON DELETE CASCADE,
    CONSTRAINT chk_toy_number_of_pieces
        CHECK (number_of_pieces IS NULL OR number_of_pieces > 0)
) COMMENT = 'Toy-specific product attributes';
