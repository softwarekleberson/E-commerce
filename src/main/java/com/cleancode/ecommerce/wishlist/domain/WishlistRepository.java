package com.cleancode.ecommerce.wishlist.domain;

import java.util.List;

public interface WishlistRepository {

	void save(WishlistItem item);

	List<WishlistItem> findByCustomerId(String customerId);

	void deleteByCustomerIdAndProductId(String customerId, String productId);
}
