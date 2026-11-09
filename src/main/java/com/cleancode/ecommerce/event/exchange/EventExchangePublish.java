package com.cleancode.ecommerce.event.exchange;

public interface EventExchangePublish {

	public void publish(ExchangeEvent event);
	public void publish(ExchangeEventAfterReviewAdm event);
}
