CREATE TABLE tb_address (
    address_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    public_id VARCHAR(36) UNIQUE NOT NULL,
    main BOOLEAN NOT NULL DEFAULT FALSE, 
    receiver VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(50) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    zip_code CHAR(9) NOT NULL,
    observation VARCHAR(255),
    street_type VARCHAR(255) NOT NULL,
    residence_type VARCHAR(255) NOT NULL,
    city VARCHAR(150) NOT NULL,
    state VARCHAR(150) NOT NULL,
    country VARCHAR(150) NOT NULL,
    customer_id VARCHAR(36), 

    CONSTRAINT fk_tb_address_customer
        FOREIGN KEY (customer_id)
        REFERENCES tb_customer(customer_id)
    	ON DELETE CASCADE
);

	CREATE INDEX idx_tb_address_public_id ON tb_address (public_id);
