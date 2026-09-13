package com.cleancode.ecommerce.order.application.usecase.item;

import com.cleancode.ecommerce.order.application.usecase.item.contract.SeparationOrder;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class SeparationOrderImpl implements SeparationOrder{

	private OrderRepository repository;
	
	public SeparationOrderImpl(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public void execute (String orderId) {
		Order order = repository.getOrderWithItensId(orderId)
				.orElseThrow(() -> new IllegalDomainOrder("Order with number : " + orderId + " not find"));
	
		order.separateItems();
		repository.save(order);
	}
}
