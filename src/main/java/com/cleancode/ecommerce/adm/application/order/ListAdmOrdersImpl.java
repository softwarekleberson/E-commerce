package com.cleancode.ecommerce.adm.application.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.adm.application.order.contract.ListAdmOrders;
import com.cleancode.ecommerce.adm.application.order.dto.AdmOrderDto;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

public class ListAdmOrdersImpl implements ListAdmOrders {
	private final OrderRepository orderRepository;

	public ListAdmOrdersImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Page<AdmOrderDto> execute(Pageable pageable) {
		return orderRepository.getAllOrders(pageable).map(AdmOrderDto::new);
	}
}
