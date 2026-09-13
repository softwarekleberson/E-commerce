package com.cleancode.ecommerce.event.ExchangeRequestAfterReview;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventExchangePublisher implements EventExchangePublish{

	private final ApplicationEventPublisher applicationEventPublisher;

	public SpringEventExchangePublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(ExchangeEvent event) {
		applicationEventPublisher.publishEvent(event);
	}

	@Override
	public void publish(ExchangeEventAfterReviewAdm event) {
		applicationEventPublisher.publishEvent(event);
	}
}
