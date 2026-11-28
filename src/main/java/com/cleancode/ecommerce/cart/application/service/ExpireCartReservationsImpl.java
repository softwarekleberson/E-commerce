package com.cleancode.ecommerce.cart.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartReservationTimeout;
import com.cleancode.ecommerce.cart.application.service.contract.ExpireCartReservations;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.wishlist.domain.WishlistItem;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

public class ExpireCartReservationsImpl implements ExpireCartReservations {

	private final CartRepository cartRepository;
	private final CartRemovalIntegrationService removalService;
	private final WishlistRepository wishlistRepository;
	private final CartReservationTimeout reservationTimeout;

	public ExpireCartReservationsImpl(CartRepository cartRepository, CartRemovalIntegrationService removalService,
			WishlistRepository wishlistRepository, CartReservationTimeout reservationTimeout) {
		this.cartRepository = cartRepository;
		this.removalService = removalService;
		this.wishlistRepository = wishlistRepository;
		this.reservationTimeout = reservationTimeout;
	}

	@Transactional
	@Override
	public int execute() {
		LocalDateTime cutoff = LocalDateTime.now().minusMinutes(reservationTimeout.getMaxDurationMinutes());
		var expiredCarts = cartRepository.findExpiredCarts(cutoff);

		for (Cart cart : expiredCarts) {
			cart.getCartItens().forEach(item -> {
				removalService.releaseStockReservation(item.getReservationId());
				wishlistRepository.save(new WishlistItem(UUID.randomUUID().toString(),
						cart.getCustomerId().getValue(), item.getProductId().getProductId(), LocalDateTime.now()));
			});
			cart.removeAllProducts();
			cartRepository.save(cart);
		}

		return expiredCarts.size();
	}
}
