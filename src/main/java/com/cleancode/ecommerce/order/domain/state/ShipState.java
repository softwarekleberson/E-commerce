package com.cleancode.ecommerce.order.domain.state;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class ShipState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.SHIP;
	}

	@Override
	public void pay(Order order) {
        throw new IllegalDomainOrder("Order has already been paid and shipped.");
	}

	@Override
	public void cancel(Order order) {
        throw new IllegalDomainOrder("Cannot cancel an order that has been shipped.");
	}

	@Override
	public void ship(Order order) {
        throw new IllegalDomainOrder("Order has already been shipped.");
	}
}