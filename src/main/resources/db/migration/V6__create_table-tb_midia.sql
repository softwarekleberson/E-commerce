CREATE TABLE tb_midia (
    midia_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier (UUID) for the media item',
    url TEXT NOT NULL COMMENT 'URL where the media is stored',
    description TEXT NOT NULL COMMENT 'Description of the media content',
    product_id VARCHAR(36) COMMENT 'Foreign key referencing the associated product',

    CONSTRAINT fk_midia_product FOREIGN KEY (product_id)
        REFERENCES tb_product(product_id)
        ON DELETE SET NULL
	) COMMENT = 'Stores media files (images, videos) related to products';

	CREATE INDEX idx_midia_product_id ON tb_midia(product_id) COMMENT 'Index to optimize queries filtering by product_id';
