CREATE TABLE tb_cart_item (
    cart_item_id VARCHAR(36) PRIMARY KEY COMMENT 'Unique identifier for this cart item',
    product_id VARCHAR(36) NOT NULL COMMENT 'Identifier of the product added to the cart',
    product_name VARCHAR(255) NOT NULL COMMENT 'Name of the product',
    url_product VARCHAR(255) NOT NULL COMMENT 'URL of the product image',
    quantity INT NOT NULL COMMENT 'Quantity of the product in the cart',
    unit_price DECIMAL(19, 2) NOT NULL COMMENT 'Price of one unit of the product',
    coin VARCHAR(20) NOT NULL COMMENT 'Currency type for the unit price',
    subtotal DECIMAL(19, 2) NOT NULL COMMENT 'Total price for this item (quantity x unit price)',
    reservation_id VARCHAR(36) NOT NULL COMMENT 'Identifier of the reservation added to the cart',
    cart_id VARCHAR(36) NOT NULL COMMENT 'Identifier of the cart this item belongs to',

    CONSTRAINT fk_cart_item_tb_cart 
        FOREIGN KEY (cart_id) REFERENCES tb_cart(cart_id) ON DELETE CASCADE,
        
    CONSTRAINT fk_cart_item_tb_product
        FOREIGN KEY (product_id) REFERENCES tb_product(product_id) ON DELETE CASCADE,
        
    CONSTRAINT fk_cart_item_tb_reservation
        FOREIGN KEY (reservation_id) REFERENCES tb_reservation(reservation_id) ON DELETE CASCADE
) COMMENT = 'Table that stores the items belonging to a shopping cart';

CREATE INDEX idx_cart_item_cart_id ON tb_cart_item(cart_id);
CREATE INDEX idx_cart_item_product_id ON tb_cart_item(product_id);
