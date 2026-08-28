package com.cleancode.ecommerce.event.order;

public interface EventTransportPublisher {

	public void publish (OrderEvent event);
}
