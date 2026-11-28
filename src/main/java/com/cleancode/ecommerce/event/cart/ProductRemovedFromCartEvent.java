package com.cleancode.ecommerce.event.cart;

public record ProductRemovedFromCartEvent(String customerId, String productId) {
}
