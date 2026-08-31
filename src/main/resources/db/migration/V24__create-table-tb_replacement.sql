CREATE TABLE tb_replacement (
    id VARCHAR(36) NOT NULL COMMENT 'Identificador único do registro de substituição',
    reservation_id VARCHAR(36) NOT NULL COMMENT 'Chave estrangeira para a reserva associada',
    reason VARCHAR(50) COMMENT 'Motivo da substituição',
    `explain` VARCHAR(255) COMMENT 'Explicação detalhada da substituição', 
    status VARCHAR(50) COMMENT 'Status atual do processo de substituição',
    customer_id VARCHAR(36) NOT NULL COMMENT 'Chave estrangeira para o cliente associado',
    quantity INT NOT NULL COMMENT 'Quantity of items replacement',
    
    CONSTRAINT pk_tb_replacement PRIMARY KEY (id),
    
    CONSTRAINT fk_replacement_reservation FOREIGN KEY (reservation_id) 
        REFERENCES tb_reservation(reservation_id) -- CORRIGIDO AQUI
        ON DELETE CASCADE,
        
    CONSTRAINT fk_replacement_customer FOREIGN KEY (customer_id)
        REFERENCES tb_customer(customer_id) -- Certifique-se de usar customer_id se em tb_customer também for esse o nome da PK
        ON DELETE CASCADE
) COMMENT='Tabela de controle de solicitações de substituição';

CREATE INDEX idx_replacement_reservation_id ON tb_replacement(reservation_id);