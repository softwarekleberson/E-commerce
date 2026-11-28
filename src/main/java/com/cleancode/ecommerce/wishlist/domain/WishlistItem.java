package com.cleancode.ecommerce.wishlist.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class WishlistItem {

	private final String id;
	private final String customerId;
	private final String productId;
	private final LocalDateTime addedAt;

	public WishlistItem(String id, String customerId, String productId, LocalDateTime addedAt) {
		this.id = Objects.requireNonNull(id, "Wishlist item ID cannot be null");
		this.customerId = Objects.requireNonNull(customerId, "Customer ID cannot be null");
		this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
		this.addedAt = Objects.requireNonNull(addedAt, "Added date cannot be null");
	}

	public String getId() {
		return id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getProductId() {
		return productId;
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}
}
