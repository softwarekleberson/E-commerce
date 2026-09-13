package com.cleancode.ecommerce.event.ExchangeRequestAfterReview;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.order.application.usecase.item.contract.OrderStatusAfterReview;

@Component
public class ExchangeStatusAfterReview {

	private final OrderStatusAfterReview afterReview;

	public ExchangeStatusAfterReview(OrderStatusAfterReview afterReview) {
		this.afterReview = afterReview;
	}
	
	@Async
	@EventListener
	public void onSeparationItem(ExchangeEventAfterReviewAdm event) {
		afterReview.execute(event.orderId(), event.reservationId(), event.flag());
	}
}
