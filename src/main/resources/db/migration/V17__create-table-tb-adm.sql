CREATE TABLE tb_adm (
    user_id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE INDEX idx_adm_email ON tb_adm(email);
