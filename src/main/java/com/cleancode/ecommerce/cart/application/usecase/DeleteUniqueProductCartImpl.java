package com.cleancode.ecommerce.cart.application.usecase;

import com.cleancode.ecommerce.cart.application.dtos.input.DeleteUniqueProductToCartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.application.usecase.contract.DeleteUniqueProductCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartItem;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.event.cart.ProductRemovedFromCartEvent;
import com.cleancode.ecommerce.event.cart.ProductRemovedFromCartEventPublisher;
import com.cleancode.ecommerce.shared.kernel.exception.IllegalDomainException;

public class DeleteUniqueProductCartImpl implements DeleteUniqueProductCart {

	private final CartRepository cartRepository;
	private final CartRemovalIntegrationService removalService; 
	private final ProductRemovedFromCartEventPublisher productRemovedFromCartEventPublisher;

	public DeleteUniqueProductCartImpl(CartRepository cartRepository, CartRemovalIntegrationService removalService,
			ProductRemovedFromCartEventPublisher productRemovedFromCartEventPublisher) {
		this.cartRepository = cartRepository;
		this.removalService = removalService;
		this.productRemovedFromCartEventPublisher = productRemovedFromCartEventPublisher;
	}

	@Override
	public void execute(String email, String cartItemId, DeleteUniqueProductToCartDto dto) {
		String customerId = removalService.resolveCustomerIdByEmail(email);

		Cart cart = cartRepository.getCartCustomer(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found for customer: " + customerId));
		
		CartItem removedItem = cart.removeProductFromCart(new CartItemId(cartItemId));
		if (!removedItem.getReservationId().equals(dto.reservationId())) {
			throw new IllegalDomainException("Reservation does not belong to the cart item");
		}
		
		removalService.releaseStockReservation(removedItem.getReservationId());
		cartRepository.save(cart);
		productRemovedFromCartEventPublisher.publish(new ProductRemovedFromCartEvent(
				customerId, removedItem.getProductId().getProductId()));
	}
}
