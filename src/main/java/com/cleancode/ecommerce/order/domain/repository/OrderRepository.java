package com.cleancode.ecommerce.order.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.state.itens.ItemStatus;

public interface OrderRepository {

	void save (Order order);
	Page <Order> getOrdersByCustomer (String customerId, Pageable pageable);
	Page <Order> getAllOrders (Pageable pageable);
	Optional<Order> getOrderWithItensId (String orderId);
	Page<Order> findItemWithStatus(ItemStatus status, Pageable pageable);
}
