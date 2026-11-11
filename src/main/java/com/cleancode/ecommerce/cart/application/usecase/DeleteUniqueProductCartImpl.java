package com.cleancode.ecommerce.cart.application.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.cart.application.dtos.input.DeleteUniqueProductToCartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.application.usecase.contract.DeleteUniqueProductCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartItem;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.wishlist.domain.WishlistItem;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

public class DeleteUniqueProductCartImpl implements DeleteUniqueProductCart {

	private final CartRepository cartRepository;
	private final CartRemovalIntegrationService removalService; 
	private final WishlistRepository wishlistRepository;

	public DeleteUniqueProductCartImpl(CartRepository cartRepository, CartRemovalIntegrationService removalService,
			WishlistRepository wishlistRepository) {
		this.cartRepository = cartRepository;
		this.removalService = removalService;
		this.wishlistRepository = wishlistRepository;
	}

	@Transactional
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
		wishlistRepository.save(new WishlistItem(UUID.randomUUID().toString(), customerId,
				removedItem.getProductId().getProductId(), LocalDateTime.now()));
		cartRepository.save(cart);
	}
}
