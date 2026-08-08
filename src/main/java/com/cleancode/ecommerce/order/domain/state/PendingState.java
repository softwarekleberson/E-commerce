package com.cleancode.ecommerce.order.domain.state;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class PendingState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.PENDING;
	}

	@Override
	public void pay(Order order) {
		order.setOrderState(new ApprovedState());
	}

	@Override
	public void cancel(Order order) {
		order.setOrderState(new CancelState());
	}

	@Override
	public void ship(Order order) {
        throw new IllegalDomainOrder("Cannot ship a pending order.");
	}
}