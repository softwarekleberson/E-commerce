package com.cleancode.ecommerce.order.domain.state.order;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class PendingState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.PENDING;
	}

	@Override
	public void pay(Order order) {
		order.setOrderState(new PaidState());
	}

	@Override
	public void pending(Order order) {
		throw new IllegalDomainOrder("Order is already pending.");
	}
}