package com.cleancode.ecommerce.order.application.usecase.item;

import com.cleancode.ecommerce.order.application.usecase.item.contract.OrderStatusAfterReview;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class OrderStatusAfterReviewImpl implements OrderStatusAfterReview {

private final OrderRepository repository;
	
	public OrderStatusAfterReviewImpl(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public void execute(String orderId, String reservationId, boolean flag) {
		Order order = repository.getOrderWithItensId(orderId)
		.orElseThrow(() -> new IllegalDomainOrder("Order not found with id " + orderId));
				
		if(flag) {
			order.exchangeAcceptedtItem(reservationId);
		}
		
		if(flag == false) {
			order.exchangeRejectedItem(reservationId);
		}
		
		repository.save(order);
	}
}
