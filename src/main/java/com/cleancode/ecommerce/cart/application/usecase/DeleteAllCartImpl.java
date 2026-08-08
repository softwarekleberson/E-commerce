package com.cleancode.ecommerce.cart.application.usecase;

import com.cleancode.ecommerce.cart.application.usecase.contract.DeleteAllCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;

public class DeleteAllCartImpl implements DeleteAllCart {

	private final CartRepository cartRepository;

	public DeleteAllCartImpl(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}

	@Override
	public void execute(String customerId) {
		Cart cart = cartRepository.getCartCustomer(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Customer with id : " + customerId + " not found"));

		cart.removeAllProducts();
		cartRepository.save(cart);
	}
}