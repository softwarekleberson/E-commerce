package com.cleancode.ecommerce.event.order;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringProductEventPublisher implements EventTransportPublisher{

	private final ApplicationEventPublisher applicationEventPublisher;

	public SpringProductEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(OrderEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
}
