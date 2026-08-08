package com.cleancode.ecommerce.order.domain.state;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;

public class CancelState implements OrderState {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.CANCEL;
	}

	@Override
	public void pay(Order order) {
		
	}

	@Override
	public void cancel(Order order) {
		
	}

	@Override
	public void ship(Order order) {
		
	}
}