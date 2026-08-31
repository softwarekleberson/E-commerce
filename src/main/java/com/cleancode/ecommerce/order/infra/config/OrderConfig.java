package com.cleancode.ecommerce.order.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.order.application.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.order.application.usecase.item.AwaitingPaymentImpl;
import com.cleancode.ecommerce.order.application.usecase.item.CancelledOrderImpl;
import com.cleancode.ecommerce.order.application.usecase.item.DeliveredOrderImpl;
import com.cleancode.ecommerce.order.application.usecase.item.SeparationOrderImpl;
import com.cleancode.ecommerce.order.application.usecase.item.TransportOrderImpl;
import com.cleancode.ecommerce.order.application.usecase.item.contract.AwaitingPayment;
import com.cleancode.ecommerce.order.application.usecase.item.contract.CancelledOrder;
import com.cleancode.ecommerce.order.application.usecase.item.contract.DeliveredOrder;
import com.cleancode.ecommerce.order.application.usecase.item.contract.SeparationOrder;
import com.cleancode.ecommerce.order.application.usecase.item.contract.TransportOrder;
import com.cleancode.ecommerce.order.application.usecase.order.ListAllOrdersImpl;
import com.cleancode.ecommerce.order.application.usecase.order.ListOrderByTransportImpl;
import com.cleancode.ecommerce.order.application.usecase.order.ListOrdersImpl;
import com.cleancode.ecommerce.order.application.usecase.order.contract.ListAllOrder;
import com.cleancode.ecommerce.order.application.usecase.order.contract.ListOrderByTransport;
import com.cleancode.ecommerce.order.application.usecase.order.contract.ListOrders;
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
	
	@Bean
	public DeliveredOrder deliveredOrder (OrderRepository repository) {
		return new DeliveredOrderImpl(repository);
	}
	
	@Bean
	public AwaitingPayment awaitingPayment(OrderRepository repository) {
		return new AwaitingPaymentImpl(repository);
	}
	
	@Bean
	public CancelledOrder cancelledOrder(OrderRepository repository) {
		return new CancelledOrderImpl(repository);
	}
	
	@Bean
	public SeparationOrder separationOrder(OrderRepository repository) {
		return new SeparationOrderImpl(repository);
	}

	@Bean
	public TransportOrder transitOrder (OrderRepository repository) {
		return new TransportOrderImpl(repository);
	}
	
	@Bean
	public ListOrderByTransport listOrderByTransport (OrderRepository repository) {
		return new ListOrderByTransportImpl(repository);
	}
}