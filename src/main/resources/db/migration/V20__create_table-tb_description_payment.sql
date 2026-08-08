CREATE TABLE tb_description_payment (
    id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for the payment description.',
    value DECIMAL(19, 2) NOT NULL COMMENT 'Amount related to this payment method',
    type_payment VARCHAR(50) NOT NULL COMMENT 'Type of payment method used',
    payment_id VARCHAR(255) NOT NULL COMMENT 'Reference to the fathers payment',

    CONSTRAINT fk_desc_payment_tb_payment 
        FOREIGN KEY (payment_id) REFERENCES tb_payment(payment_id) ON DELETE CASCADE
) COMMENT = 'Detailed description of the payment methods used in a transaction.';

CREATE INDEX idx_desc_payment_id ON tb_description_payment(payment_id);