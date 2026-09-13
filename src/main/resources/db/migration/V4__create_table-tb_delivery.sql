CREATE TABLE tb_delivery (
    delivery_id BIGINT AUTO_INCREMENT COMMENT 'Identificador sequencial único da entrega',
    address_id BIGINT NOT NULL COMMENT 'Chave estrangeira para o endereço de entrega associado',
    delivery_phrase VARCHAR(255) COMMENT 'Instruções especiais ou frase para a entrega',

    CONSTRAINT pk_tb_delivery PRIMARY KEY (delivery_id),

    CONSTRAINT fk_tb_delivery_address
        FOREIGN KEY (address_id)
        REFERENCES tb_address(address_id)
        ON DELETE CASCADE
) COMMENT='Tabela de associação entre entregas, endereços e instruções';