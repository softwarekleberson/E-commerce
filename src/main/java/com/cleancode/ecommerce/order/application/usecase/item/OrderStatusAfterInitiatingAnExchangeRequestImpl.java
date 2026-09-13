package com.cleancode.ecommerce.order.application.usecase.item;

import com.cleancode.ecommerce.order.application.usecase.item.contract.OrderStatusAfterInitiatingAnExchangeRequest;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class OrderStatusAfterInitiatingAnExchangeRequestImpl implements OrderStatusAfterInitiatingAnExchangeRequest{

	private final OrderRepository repository;
	
	public OrderStatusAfterInitiatingAnExchangeRequestImpl(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public void execute(String orderId, String reservationId) {
		Order order = repository.getOrderWithItensId(orderId)
		.orElseThrow(() -> new IllegalDomainOrder("Order not found with id " + orderId));
		
		order.exchangeRequestItem(reservationId);
		repository.save(order);
	}
}
