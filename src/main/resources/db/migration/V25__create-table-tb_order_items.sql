-- Tabela de itens do pedido
CREATE TABLE tb_order_items (
    order_item_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for the order item.',
    order_id VARCHAR(36) NOT NULL COMMENT 'Reference to the fathers request',
    product_id VARCHAR(255) NOT NULL COMMENT 'Product name at the time of the transaction',
    price DECIMAL(19, 2) NOT NULL COMMENT 'Unit price of the item',
    quantity INT NOT NULL COMMENT 'Quantity purchased',
    subtotal DECIMAL(19, 2) NOT NULL COMMENT 'Item subtotal (price x quantity)',
    item_status ENUM('AWAITING_PAYMENT', 'CANCELLED', 'SEPARATING', 'SHIPPED', 'DELIVERED', 'EXCHANGE_REQUEST', 'EXCHANGE_ACCEPTED', 'EXCHANGE_REJECTED') NOT NULL DEFAULT 'AWAITING_PAYMENT',
    reservation_id VARCHAR(36) NOT NULL COMMENT 'Reference to the stock outflow record',

    CONSTRAINT fk_order_items_tb_orders 
        FOREIGN KEY (order_id) REFERENCES tb_orders(order_id) ON DELETE CASCADE
) COMMENT = 'Table that stores the order items linked to an order.';

CREATE INDEX idx_order_items_order_id ON tb_order_items(order_id);
CREATE INDEX idx_order_items_reservation_id ON tb_order_items(reservation_id);