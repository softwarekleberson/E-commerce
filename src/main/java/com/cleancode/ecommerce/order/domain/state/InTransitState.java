package com.cleancode.ecommerce.order.domain.state;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class InTransitState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.INTRANSIT; // ou OrderStatus.SHIPPED
	}

	@Override
	public void pay(Order order) {
		throw new IllegalDomainOrder("Order has already been paid and shipped.");
	}

	@Override
	public void cancel(Order order) {
		throw new IllegalDomainOrder("Cannot cancel an order that has already been shipped.");
	}

	@Override
	public void ship(Order order) {
		throw new IllegalDomainOrder("Order is already in transit.");
	}

	@Override
	public void delivered(Order order) {
		order.setOrderState(new DeliveredState());
	}

	@Override
	public void pending(Order order) {
		throw new IllegalDomainOrder("Cannot revert an order in transit back to pending.");
	}
}