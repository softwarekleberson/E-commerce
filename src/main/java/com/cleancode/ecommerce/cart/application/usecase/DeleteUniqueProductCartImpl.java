package com.cleancode.ecommerce.cart.application.usecase;

import com.cleancode.ecommerce.cart.application.dtos.input.DeleteUniqueProductToCartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.application.usecase.contract.DeleteUniqueProductCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;

public class DeleteUniqueProductCartImpl implements DeleteUniqueProductCart {

	private final CartRepository cartRepository;
	private final CartRemovalIntegrationService removalService; 

	public DeleteUniqueProductCartImpl(CartRepository cartRepository, CartRemovalIntegrationService removalService) {
		this.cartRepository = cartRepository;
		this.removalService = removalService;
	}

	@Override
	public void execute(String email, String cartItemId, DeleteUniqueProductToCartDto dto) {
		String customerId = removalService.resolveCustomerIdByEmail(email);

		Cart cart = cartRepository.getCartCustomer(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found for customer: " + customerId));
		
		cart.removeProductFromCart(new CartItemId(cartItemId));
		
		removalService.releaseStockReservation(dto.reservationId());
		cartRepository.save(cart);
	}
}