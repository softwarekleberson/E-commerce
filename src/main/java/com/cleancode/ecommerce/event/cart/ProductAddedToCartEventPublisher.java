package com.cleancode.ecommerce.event.cart;

public interface ProductAddedToCartEventPublisher {

	void publish(ProductAddedToCartEvent event);
}
