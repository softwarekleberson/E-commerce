package com.cleancode.ecommerce.event.product;

public interface EventPublisher {

	public void publish(ProductEvent event);
	public void publish (StockUpdatedEvent event);
}
