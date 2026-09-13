package com.cleancode.ecommerce.event.ExchangeRequestAfterReview;

public interface EventExchangePublish {

	public void publish(ExchangeEvent event);
	public void publish(ExchangeEventAfterReviewAdm event);
}
