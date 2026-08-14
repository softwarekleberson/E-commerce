CREATE TABLE tb_replacement (
    id VARCHAR(36) NOT NULL,
    reservation_id VARCHAR(36) NOT NULL,
    reason VARCHAR(50),
    `explain` VARCHAR(255), 
    status VARCHAR(50),
    
    CONSTRAINT pk_tb_replacement PRIMARY KEY (id),
    CONSTRAINT fk_replacement_reservation FOREIGN KEY (reservation_id) 
        REFERENCES tb_reservation(reservation_id) 
        ON DELETE CASCADE
);

-- Índice para melhorar a performance em JOINS por reservation_id
CREATE INDEX idx_replacement_reservation_id ON tb_replacement(reservation_id);