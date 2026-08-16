package com.cleancode.ecommerce.order.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.order.domain.Order;

public interface OrderRepository {

	void save (Order order);
	Page <Order> getOrdersByCustomer (String customerId, Pageable pageable);
	Page <Order> getAllOrders (Pageable pageable);
}
