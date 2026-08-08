package com.cleancode.ecommerce.order.infra.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleancode.ecommerce.order.infra.persistencia.OrderEntity;

public interface OrderJpa extends JpaRepository<OrderEntity, String>{

}
