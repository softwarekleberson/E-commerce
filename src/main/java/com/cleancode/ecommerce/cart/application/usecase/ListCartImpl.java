package com.cleancode.ecommerce.cart.application.usecase;

import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartQueryIntegrationService;
import com.cleancode.ecommerce.cart.application.usecase.contract.ListCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;

public class ListCartImpl implements ListCart {

	private final CartRepository cartRepository;
	private final CartQueryIntegrationService queryService; 

	public ListCartImpl(CartRepository cartRepository, CartQueryIntegrationService queryService) {
		this.cartRepository = cartRepository;
		this.queryService = queryService;
	}

	@Override
	public CartDto execute(String email) {
		String customerId = queryService.resolveCustomerIdByEmail(email);
		
		Cart cart = cartRepository.getCartCustomer(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found for customer ID: " + customerId));

		return new CartDto(cart);
	}
}