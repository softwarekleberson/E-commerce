package com.cleancode.ecommerce.wishlist.infra.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.event.cart.ProductAddedToCartEvent;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

@Component
public class ProductAddedToCartEventListener {

	private final WishlistRepository wishlistRepository;

	public ProductAddedToCartEventListener(WishlistRepository wishlistRepository) {
		this.wishlistRepository = wishlistRepository;
	}

	@EventListener
	public void onProductAddedToCart(ProductAddedToCartEvent event) {
		wishlistRepository.deleteByCustomerIdAndProductId(event.customerId(), event.productId());
	}
}
