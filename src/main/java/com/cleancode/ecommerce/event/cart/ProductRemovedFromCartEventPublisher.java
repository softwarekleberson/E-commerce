package com.cleancode.ecommerce.event.cart;

public interface ProductRemovedFromCartEventPublisher {

	void publish(ProductRemovedFromCartEvent event);
}
