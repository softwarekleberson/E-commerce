package com.cleancode.ecommerce.order.application.usecase.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.order.application.usecase.order.contract.ListOrderByTransport;
import com.cleancode.ecommerce.order.application.usecase.order.dto.ListOrdersDto;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.order.domain.state.itens.ItemStatus;

public class ListOrderByTransportImpl implements ListOrderByTransport{

	private final OrderRepository repository;

	public ListOrderByTransportImpl(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public Page<ListOrdersDto> execute(ItemStatus status, Pageable pageable){
		
		Page<Order> orders = repository.findItemWithStatus(status, pageable);
		return orders.map(ListOrdersDto::new);
	}
}
