package com.cleancode.ecommerce.order.infra.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.order.infra.mapper.OrderMapper;
import com.cleancode.ecommerce.order.infra.persistencia.OrderEntity;

import jakarta.transaction.Transactional;

@Repository
public class OrderRepositoryJpa implements OrderRepository{

	private final OrderJpa jpa;
	
	public OrderRepositoryJpa(OrderJpa jpa) {
		this.jpa = jpa;
	}
	
	@Transactional
	@Override
	public void save(Order order) {
		OrderEntity entity = OrderMapper.toEntity(order);
		jpa.save(entity);
	}

	@Override
	public Page<Order> getOrdersByCustomer(String customerId, Pageable pageable) {
		return jpa.findByCustomerId(customerId, pageable)
				.map(OrderMapper::toDomain);
	}
}
