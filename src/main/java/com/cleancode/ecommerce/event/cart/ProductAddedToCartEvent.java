package com.cleancode.ecommerce.event.cart;

public record ProductAddedToCartEvent(String customerId, String productId) {
}
