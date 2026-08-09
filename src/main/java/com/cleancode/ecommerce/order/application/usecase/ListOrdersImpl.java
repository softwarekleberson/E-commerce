package com.cleancode.ecommerce.order.application.usecase;

import com.cleancode.ecommerce.order.application.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.order.application.usecase.contract.ListOrders;
import com.cleancode.ecommerce.order.application.usecase.dto.ListOrdersDto;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListOrdersImpl implements ListOrders{

	private final OrderRepository repository;
	private final CustomerIdentityIdService service;
	
	public ListOrdersImpl(OrderRepository repository, CustomerIdentityIdService service) {
		this.repository = repository;
		this.service = service;
	}
	
	@Override
	public Page<ListOrdersDto> execute(String email, Pageable pageable) {
		
		String customerId = service.customerId(email).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		Page <Order> orders = repository.getOrdersByCustomer(customerId, pageable);
		return orders.map(ListOrdersDto::new);
	}
}
