package com.cleancode.ecommerce.order.application.usecase.item;

import com.cleancode.ecommerce.order.application.usecase.item.contract.CancelledOrder;
import com.cleancode.ecommerce.order.application.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class CancelledOrderImpl implements CancelledOrder{

	private final OrderRepository repository;
	private final CustomerIdentityIdService customerIdentity;

	public CancelledOrderImpl(OrderRepository repository, CustomerIdentityIdService customerIdentity) {
		this.repository = repository;
		this.customerIdentity = customerIdentity;
	}

	@Override
	public void execute (String orderId, String reservationId, String customerEmail) {
		String customerId = customerIdentity.customerId(customerEmail)
				.orElseThrow(() -> new IllegalDomainOrder("Order not found"));

		Order order = repository.getOrderWithItensId(orderId)
				.orElseThrow(() -> new IllegalDomainOrder("Order not found"));
		if (!order.getCustomerId().getValue().equals(customerId)) {
			throw new IllegalDomainOrder("Order not found");
		}
	
		order.cancelledItem(reservationId);
		repository.save(order);
	}
}
