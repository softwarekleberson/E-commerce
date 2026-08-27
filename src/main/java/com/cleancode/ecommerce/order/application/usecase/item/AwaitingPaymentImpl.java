package com.cleancode.ecommerce.order.application.usecase.item;

import com.cleancode.ecommerce.order.application.usecase.item.contract.AwaitingPayment;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class AwaitingPaymentImpl implements AwaitingPayment{

	private OrderRepository repository;
	
	public AwaitingPaymentImpl(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public void execute (String orderId, String reservationId) {
		Order order = repository.getOrderWithItensId(orderId)
				.orElseThrow(() -> new IllegalDomainOrder("Order with number : " + orderId + "not find"));
	
		OrderItem item = order.findItemByReservationId(reservationId);
		item.awaitingPayment();
		repository.save(order);
	}
}
