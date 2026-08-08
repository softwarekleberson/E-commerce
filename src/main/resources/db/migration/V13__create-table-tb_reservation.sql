CREATE TABLE tb_reservation (
    reservation_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for reservation',
    cart_id VARCHAR(36) NOT NULL COMMENT 'Identifier of the cart associated with this reservation',
    customer_id VARCHAR(36) NOT NULL COMMENT 'Identifier of the customer making the reservation',
    quantity INT NOT NULL COMMENT 'Quantity of items reserved',
    reservation_time TIMESTAMP NOT NULL COMMENT 'Timestamp when the reservation was made',
    reserve_status VARCHAR(50) NOT NULL COMMENT 'Current status of the reservation',
    stock_id VARCHAR(36) NOT NULL COMMENT 'Stock record related to the reservation',
    
    CONSTRAINT fk_reservation_stock FOREIGN KEY (stock_id) REFERENCES tb_stock(stock_id) ON DELETE CASCADE,
    CONSTRAINT fk_reservation_cart FOREIGN KEY (cart_id) REFERENCES tb_cart(cart_id) ON DELETE CASCADE,
    CONSTRAINT fk_reservation_customer FOREIGN KEY (customer_id) REFERENCES tb_customer(customer_id) ON DELETE CASCADE
);

CREATE INDEX idx_reservation_customer_id ON tb_reservation(customer_id);
CREATE INDEX idx_reservation_stock_id ON tb_reservation(stock_id);
CREATE INDEX idx_reservation_cart_id ON tb_reservation(cart_id);
