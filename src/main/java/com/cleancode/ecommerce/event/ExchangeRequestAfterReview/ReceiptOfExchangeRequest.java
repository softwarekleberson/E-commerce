package com.cleancode.ecommerce.event.ExchangeRequestAfterReview;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.order.application.usecase.item.contract.OrderStatusAfterInitiatingAnExchangeRequest;

@Component
public class ReceiptOfExchangeRequest {

	private final OrderStatusAfterInitiatingAnExchangeRequest request;

	public ReceiptOfExchangeRequest(OrderStatusAfterInitiatingAnExchangeRequest request) {
		this.request = request;
	}
	
	@Async
	@EventListener
	public void onSeparationItem(ExchangeEvent event) {
		request.execute(event.orderId(), event.resevationId());
	}
}
