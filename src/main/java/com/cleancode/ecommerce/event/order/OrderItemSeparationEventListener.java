package com.cleancode.ecommerce.event.order;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.order.application.usecase.item.contract.SeparationOrder;

@Component
public class OrderItemSeparationEventListener {

	private final SeparationOrder separationOrder;

	public OrderItemSeparationEventListener(SeparationOrder separationOrder) {
		this.separationOrder = separationOrder;
	}
	
	@Async
	@EventListener
	public void onSeparationItem (OrderEvent event) {
		separationOrder.execute(event.orderId());
	}
}