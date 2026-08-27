CREATE TABLE tb_charge (
    charge_id BIGINT AUTO_INCREMENT COMMENT 'Identificador sequencial único da cobrança',
    address_id BIGINT NOT NULL COMMENT 'Chave estrangeira para o endereço de cobrança associado',
    
    CONSTRAINT pk_tb_charge PRIMARY KEY (charge_id),

    CONSTRAINT fk_tb_charge_address
        FOREIGN KEY (address_id)
        REFERENCES tb_address(address_id)
        ON DELETE CASCADE
) COMMENT='Tabela de associação entre cobranças e endereços';