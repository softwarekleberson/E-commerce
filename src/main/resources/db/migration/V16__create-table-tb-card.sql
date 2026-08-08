CREATE TABLE tb_card (
    card_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for the card (UUID format)',
    customer_id VARCHAR(36) NOT NULL COMMENT 'Foreign key referencing the customer who owns this card',
    main BOOLEAN NOT NULL COMMENT 'Indicates whether this is the customer primary card',
    printed_name VARCHAR(255) NOT NULL COMMENT 'Name printed on the card',
    code CHAR(4) NOT NULL COMMENT 'Card security code (CVV)',
    number_card CHAR(16) NOT NULL COMMENT 'Card number (should be encrypted or tokenized)',
    expiration_date DATE NOT NULL COMMENT 'Card expiration date',
    flag VARCHAR(50) NOT NULL COMMENT 'Card brand/flag (e.g., VISA, MASTERCARD, ELO)',

    CONSTRAINT fk_tb_card_customer
    FOREIGN KEY (customer_id)
    REFERENCES tb_customer(customer_id)
    ON DELETE CASCADE
);

-- 🔑 Indexes for performance
CREATE INDEX idx_tb_card_customer ON tb_card (customer_id);
CREATE INDEX idx_tb_card_flag ON tb_card (flag);
CREATE INDEX idx_tb_card_main ON tb_card (main);