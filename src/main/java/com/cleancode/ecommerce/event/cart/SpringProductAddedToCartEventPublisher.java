package com.cleancode.ecommerce.event.cart;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringProductAddedToCartEventPublisher implements ProductAddedToCartEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public SpringProductAddedToCartEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(ProductAddedToCartEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
}
