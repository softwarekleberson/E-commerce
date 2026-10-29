package com.cleancode.ecommerce.cart.application.usecase;

import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartQueryIntegrationService;
import com.cleancode.ecommerce.cart.application.usecase.contract.ListCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.order.domain.shipping.ShippingCostStrategyFactory;

public class ListCartImpl implements ListCart {

	private final CartRepository cartRepository;
	private final CartQueryIntegrationService queryService; 
	private final ShippingCostStrategyFactory shippingStrategyFactory;

	public ListCartImpl(CartRepository cartRepository, CartQueryIntegrationService queryService,
			ShippingCostStrategyFactory shippingStrategyFactory) {
		this.cartRepository = cartRepository;
		this.queryService = queryService;
		this.shippingStrategyFactory = shippingStrategyFactory;
	}

	@Override
	public CartDto execute(String email) {
		String customerId = queryService.resolveCustomerIdByEmail(email);
		
		Cart cart = cartRepository.getCartCustomer(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found for customer ID: " + customerId));

		return new CartDto(cart, shippingStrategyFactory.calculate(cart.getTotalPrice().getPrice()));
	}
}