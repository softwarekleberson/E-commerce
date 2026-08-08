CREATE TABLE tb_voucher (
    voucher_id VARCHAR(255) PRIMARY KEY COMMENT 'Identificador único do voucher',
    customer_id VARCHAR(255) NOT NULL COMMENT 'Identificador do cliente que recebeu o voucher',
    message TEXT NOT NULL COMMENT 'Mensagem associada ao voucher',
    emission DATE NOT NULL COMMENT 'Data de emissão do voucher',
    type_voucher VARCHAR(50) NOT NULL COMMENT 'Tipo do voucher (enum TypeVoucherEntity)',
    discount DOUBLE NOT NULL COMMENT 'Valor do desconto aplicado pelo voucher',
    active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Indica se o voucher está ativo (TRUE) ou inativo (FALSE)'
) COMMENT='Tabela que armazena os vouchers emitidos para os clientes';

-- Índices para otimizar consultas
CREATE INDEX idx_voucher_customer ON tb_voucher(customer_id);
CREATE INDEX idx_voucher_type ON tb_voucher(type_voucher);
CREATE INDEX idx_voucher_emission ON tb_voucher(emission);
CREATE INDEX idx_voucher_active ON tb_voucher(active);
