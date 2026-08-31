package com.cleancode.ecommerce.order.application.usecase.item;

import com.cleancode.ecommerce.order.application.usecase.item.contract.CancelledOrder;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class CancelledOrderImpl implements CancelledOrder{

	private OrderRepository repository;

	public CancelledOrderImpl(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public void execute (String orderId, String reservationId) {
		Order order = repository.getOrderWithItensId(orderId)
				.orElseThrow(() -> new IllegalDomainOrder("Order with number : " + orderId + "not find"));
	
		order.cancelledItem(reservationId);
		repository.save(order);
	}
}