package com.cleancode.ecommerce.event.product;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublisher implements EventPublisher{

	private final ApplicationEventPublisher applicationEventPublisher;
	
	public SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(ProductEvent event) {
		applicationEventPublisher.publishEvent(event);
	}

	@Override
	public void publish(StockUpdatedEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
}