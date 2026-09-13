package com.cleancode.ecommerce.replacement.application.service;

import java.util.Optional;

import com.cleancode.ecommerce.order.domain.Order;

public interface FindOrderByIdService {

	public Optional<Order> findOrderById(String orderId);
}
