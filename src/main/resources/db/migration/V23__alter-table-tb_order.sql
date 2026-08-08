ALTER TABLE
tb_payment
ADD COLUMN
order_id VARCHAR(36),

ADD CONSTRAINT fk_payment_order
	FOREIGN KEY (order_id)
	REFERENCES tb_orders(order_id)
	ON DELETE CASCADE
