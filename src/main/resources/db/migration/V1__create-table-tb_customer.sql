CREATE TABLE tb_customer (
	customer_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for the customer',
    system_client_status BOOLEAN DEFAULT TRUE,
	cpf VARCHAR(15) NOT NULL COMMENT 'Brazilian CPF document number and (., -)',
    full_name VARCHAR(150) NOT NULL COMMENT 'Customer full name',
    birth_date DATE NOT NULL COMMENT 'Customer birth date',
    password_hash VARCHAR(512) NOT NULL COMMENT 'Hashed customer password',
    gender ENUM('MALE', 'FEMALE', 'NOT_INFORMED') NOT NULL COMMENT 'Customer gender',
    area_code CHAR(2) NOT NULL COMMENT 'Phone area code (DDD)',
    phone_number VARCHAR(9) NOT NULL COMMENT 'Phone number without area code',
    phone_type ENUM('MOBILE', 'WHATSAPP', 'LANDLINE') NOT NULL COMMENT 'Type of phone contact',
    email VARCHAR(255) NOT NULL COMMENT 'Customer email address',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp',
    
    CONSTRAINT uq_tb_customer_email UNIQUE (email)
);

	CREATE INDEX idx_tb_customer_cpf ON tb_customer (cpf);
	CREATE INDEX idx_tb_customer_email ON tb_customer (email);
	CREATE FULLTEXT INDEX idx_tb_customer_full_name ON tb_customer (full_name);
	