package com.cleancode.ecommerce.order.domain.state;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class DeliveredState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.DELIVERED;
	}

	@Override
	public void pay(Order order) {
		throw new IllegalDomainOrder("Cannot pay an order that has already been delivered.");
	}

	@Override
	public void cancel(Order order) {
		throw new IllegalDomainOrder("A delivered order cannot be cancelled.");
	}

	@Override
	public void ship(Order order) {
		throw new IllegalDomainOrder("A delivered order cannot be shipped again.");
	}

	@Override
	public void delivered(Order order) {
		throw new IllegalDomainOrder("A delivered order cannot be delivered again.");
	}

	@Override
	public void pending(Order order) {
		throw new IllegalDomainOrder("Cannot revert a delivered order back to pending.");
	}
}