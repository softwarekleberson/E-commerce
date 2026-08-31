package com.cleancode.ecommerce.order.domain.state.order;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class PaidState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.PAID;
	}

	@Override
	public void pay(Order order) {
		throw new IllegalDomainOrder("Order is already paid.");
	}

	@Override
	public void pending(Order order) {
		throw new IllegalDomainOrder("Cannot revert a paid order back to pending.");
	}
}