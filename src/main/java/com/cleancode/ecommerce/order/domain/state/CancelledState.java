package com.cleancode.ecommerce.order.domain.state;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class CancelledState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.CANCELLED;
	}

	@Override
	public void pay(Order order) {
		throw new IllegalDomainOrder("Cannot pay a cancelled order.");
	}

	@Override
	public void cancel(Order order) {
		throw new IllegalDomainOrder("Order is already cancelled.");
	}

	@Override
	public void ship(Order order) {
		throw new IllegalDomainOrder("Cannot ship a cancelled order.");
	}

	@Override
	public void delivered(Order order) {
		throw new IllegalDomainOrder("Cannot deliver a cancelled order.");
	}

	@Override
	public void pending(Order order) {
		throw new IllegalDomainOrder("Cannot revert a cancelled order back to pending.");
	}
}