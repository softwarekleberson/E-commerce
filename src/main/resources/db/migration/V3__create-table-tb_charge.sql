CREATE TABLE tb_charge (
    charge_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address_id BIGINT NOT NULL,
    
    CONSTRAINT fk_tb_charge_address
        FOREIGN KEY (address_id)
        REFERENCES tb_address(address_id)
        ON DELETE CASCADE
);
