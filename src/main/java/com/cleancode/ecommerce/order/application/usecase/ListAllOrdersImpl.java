package com.cleancode.ecommerce.order.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.order.application.usecase.contract.ListAllOrder;
import com.cleancode.ecommerce.order.application.usecase.dto.ListOrdersDto;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class ListAllOrdersImpl implements ListAllOrder {

	private final OrderRepository repository;
	
	public ListAllOrdersImpl(OrderRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Page<ListOrdersDto> execute(Pageable pageable){
		Page<Order> orders = repository.getAllOrders(pageable);
		return orders.map(ListOrdersDto::new);
	}
}