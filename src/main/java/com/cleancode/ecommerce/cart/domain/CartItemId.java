package com.cleancode.ecommerce.cart.domain;

import java.util.Objects;
import java.util.UUID;

public class CartItemId {

	private final String cartItemId;
	
	public CartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	
	public CartItemId() {
		this.cartItemId = UUID.randomUUID().toString();
	}
	
	public String getCartItemId() {
		return cartItemId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartItemId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItemId other = (CartItemId) obj;
		return Objects.equals(cartItemId, other.cartItemId);
	}

	@Override
	public String toString() {
		return "CartItemId [cartItemId=" + cartItemId + "]";
	}
}
