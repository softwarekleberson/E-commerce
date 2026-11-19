package com.cleancode.ecommerce.event.cart;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringProductRemovedFromCartEventPublisher implements ProductRemovedFromCartEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public SpringProductRemovedFromCartEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(ProductRemovedFromCartEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
}
