package com.cleancode.ecommerce.wishlist.infra.event;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.event.cart.ProductRemovedFromCartEvent;
import com.cleancode.ecommerce.wishlist.domain.WishlistItem;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

@Component
public class ProductRemovedFromCartEventListener {

	private final WishlistRepository wishlistRepository;

	public ProductRemovedFromCartEventListener(WishlistRepository wishlistRepository) {
		this.wishlistRepository = wishlistRepository;
	}

	@EventListener
	public void onProductRemovedFromCart(ProductRemovedFromCartEvent event) {
		wishlistRepository.save(new WishlistItem(UUID.randomUUID().toString(), event.customerId(),
				event.productId(), LocalDateTime.now()));
	}
}
