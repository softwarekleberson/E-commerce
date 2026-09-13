-- Tabela de pedidos
CREATE TABLE tb_orders (
    order_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique order identifier',
    customer_id VARCHAR(36) NOT NULL COMMENT 'Customer identifier who placed the order.',
    delivery_id VARCHAR(36) COMMENT 'Delivery identifier associated with the order.',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date and time the order was created.',
    value DECIMAL(19, 2) NOT NULL COMMENT 'Total order value',
    type_coin VARCHAR(10) NOT NULL COMMENT 'Currency of the total order',
    status ENUM('PAID', 'PENDING') NOT NULL DEFAULT 'PENDING' COMMENT 'Current order status',
    delivered BOOLEAN NOT NULL
) COMMENT = 'Table that stores orders placed by customers.';

CREATE INDEX idx_orders_customer_id ON tb_orders(customer_id);
CREATE INDEX idx_orders_delivery_id ON tb_orders(delivery_id);
CREATE INDEX idx_orders_status ON tb_orders(status);