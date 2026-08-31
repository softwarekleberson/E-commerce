CREATE TABLE tb_adm (
    user_id VARCHAR(36) NOT NULL COMMENT 'Identificador único do administrador',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'Endereço de e-mail de acesso do administrador',
    password VARCHAR(255) NOT NULL COMMENT 'Hash da senha de autenticação',
    
    CONSTRAINT pk_tb_adm PRIMARY KEY (user_id)
) COMMENT='Tabela de cadastro e autenticação dos administradores';

CREATE INDEX idx_adm_email ON tb_adm(email);