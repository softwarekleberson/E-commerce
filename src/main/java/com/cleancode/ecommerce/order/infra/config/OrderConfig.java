package com.cleancode.ecommerce.order.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.order.application.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.order.application.usecase.ListAllOrdersImpl;
import com.cleancode.ecommerce.order.application.usecase.ListOrdersImpl;
import com.cleancode.ecommerce.order.application.usecase.contract.ListAllOrder;
import com.cleancode.ecommerce.order.application.usecase.contract.ListOrders;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;

@Configuration
public class OrderConfig {

	@Bean
	public ListOrders listOrders(OrderRepository repositoy, CustomerIdentityIdService service) {
		return new ListOrdersImpl(repositoy, service);
	}
	
	@Bean
	public ListAllOrder listAllOrder (OrderRepository repository) {
		return new ListAllOrdersImpl(repository);
	}
}
