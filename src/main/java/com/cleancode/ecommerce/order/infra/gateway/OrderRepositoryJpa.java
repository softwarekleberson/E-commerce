package com.cleancode.ecommerce.order.infra.gateway;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.order.domain.state.itens.ItemStatus;
import com.cleancode.ecommerce.order.infra.mapper.OrderMapper;
import com.cleancode.ecommerce.order.infra.persistencia.ItemStatusEntity;
import com.cleancode.ecommerce.order.infra.persistencia.OrderEntity;
import com.cleancode.ecommerce.replacement.application.service.ValueUnitProductService;

import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderRepositoryJpa implements OrderRepository, ValueUnitProductService{

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

	@Override
	public Page<Order> getAllOrders(Pageable pageable) {
		return jpa.findAll(pageable)
				.map(OrderMapper::toDomain);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Order> getOrderWithItensId(String orderId) {
		return jpa.findByIdWithItems(orderId)
				.map(OrderMapper::toDomain);
	}

	@Override
	public Page<Order> findItemWithStatus(ItemStatus status, Pageable pageable) {
		
		ItemStatusEntity entityStatus = ItemStatusEntity.valueOf(status.name());
		
		return jpa.findByItemStatus(entityStatus, pageable)
			.map(OrderMapper::toDomain);
	}

	@Override
	public Optional<BigDecimal> findSubtotalByReservationId(String reservationId) {
		return jpa.findSubtotalByReservationId(reservationId);
	}
}