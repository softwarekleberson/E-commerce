package com.cleancode.ecommerce.order.application.usecase.order;

import com.cleancode.ecommerce.order.application.usecase.order.contract.CustomerConfirmsDelivery;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class CustomerConfirmsDeliveryImpl implements CustomerConfirmsDelivery{

	private OrderRepository repository;
	
	@Override
	public void execute (String orderId) {
		Order order = repository.getOrderWithItensId(orderId)
				.orElseThrow(() -> new IllegalDomainOrder("Order with number : " + orderId + "not find"));
	
		order.customerConfirmDeliverd();
		repository.save(order);
	}
}
