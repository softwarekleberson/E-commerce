package com.cleancode.ecommerce.wishlist.application.dto;

import java.time.LocalDateTime;

public record WishlistItemDto(String productId, LocalDateTime addedAt) {
}
