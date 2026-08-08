CREATE TABLE tb_payment (
    payment_id VARCHAR(255) PRIMARY KEY COMMENT 'Unique payment identifier',
    customer_id VARCHAR(255) NOT NULL COMMENT 'Customer identifier who made the payment.',
    payment_date DATETIME NOT NULL COMMENT 'Date and time the payment was recorded.',
    total DECIMAL(19, 2) NOT NULL COMMENT 'Total payment amount',
    type_coin VARCHAR(20) NOT NULL COMMENT 'Type of currency used',
    status_payment VARCHAR(50) NOT NULL COMMENT 'Current payment status',
    
    INDEX idx_payment_customer (customer_id),
    INDEX idx_payment_status (status_payment),
    INDEX idx_payment_date (payment_date)
) COMMENT = 'Main table that stores payment transactions.';