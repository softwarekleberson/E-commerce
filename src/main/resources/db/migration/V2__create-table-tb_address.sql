CREATE TABLE tb_address (
    address_id BIGINT AUTO_INCREMENT COMMENT 'Identificador sequencial único do endereço',
    public_id VARCHAR(36) UNIQUE NOT NULL COMMENT 'Identificador público/UUID do endereço',
    main BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Indica se este é o endereço principal do cliente', 
    receiver VARCHAR(255) NOT NULL COMMENT 'Nome do destinatário que receberá as entregas',
    street VARCHAR(255) NOT NULL COMMENT 'Nome do logradouro/rua',
    number VARCHAR(50) NOT NULL COMMENT 'Número do imóvel ou lote',
    neighborhood VARCHAR(255) NOT NULL COMMENT 'Bairro ou distrito',
    zip_code CHAR(9) NOT NULL COMMENT 'Código de Endereçamento Postal (CEP)',
    observation VARCHAR(255) COMMENT 'Observações ou pontos de referência adicionais',
    street_type VARCHAR(255) NOT NULL COMMENT 'Tipo do logradouro (ex: Rua, Avenida, Alameda)',
    residence_type VARCHAR(255) NOT NULL COMMENT 'Tipo da residência (ex: Casa, Apartamento, Comercial)',
    city VARCHAR(150) NOT NULL COMMENT 'Nome da cidade',
    state VARCHAR(150) NOT NULL COMMENT 'Nome ou sigla do estado',
    country VARCHAR(150) NOT NULL COMMENT 'Nome do país',
    customer_id VARCHAR(36) COMMENT 'Chave estrangeira para o cliente proprietário do endereço', 

    CONSTRAINT pk_tb_address PRIMARY KEY (address_id),

    CONSTRAINT fk_tb_address_customer
        FOREIGN KEY (customer_id)
        REFERENCES tb_customer(customer_id)
        ON DELETE CASCADE
) COMMENT='Tabela de cadastro e gestão de endereços dos clientes';

CREATE INDEX idx_tb_address_public_id ON tb_address (public_id);