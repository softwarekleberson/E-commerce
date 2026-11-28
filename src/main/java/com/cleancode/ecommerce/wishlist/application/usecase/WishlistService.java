package com.cleancode.ecommerce.wishlist.application.usecase;

import java.util.List;

import com.cleancode.ecommerce.wishlist.application.dto.WishlistItemDto;

public interface WishlistService {

	List<WishlistItemDto> list(String email);

	void remove(String email, String productId);
}
