package com.cleancode.ecommerce.order.domain.state;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class ApprovedState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.PAY;
	}

	@Override
	public void pay(Order order) {
		throw new IllegalDomainOrder("Order is already paid/approved.");
	}

	@Override
	public void cancel(Order order) {
        throw new IllegalDomainOrder("Cannot cancel an approved order.");
	}

	@Override
	public void ship(Order order) {
		order.setOrderState(new ShipState());
	}
}