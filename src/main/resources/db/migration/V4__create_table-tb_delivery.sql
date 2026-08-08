CREATE TABLE tb_delivery (
    delivery_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address_id BIGINT NOT NULL,
    delivery_phrase VARCHAR(255),

    CONSTRAINT fk_tb_delivery_address
        FOREIGN KEY (address_id)
        REFERENCES tb_address(address_id)
        ON DELETE CASCADE
);
