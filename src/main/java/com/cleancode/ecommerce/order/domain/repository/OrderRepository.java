package com.cleancode.ecommerce.order.domain.repository;

import com.cleancode.ecommerce.order.domain.Order;

public interface OrderRepository {

	void save (Order order);
}
