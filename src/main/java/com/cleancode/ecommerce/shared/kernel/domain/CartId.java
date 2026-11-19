package com.cleancode.ecommerce.cart.domain;

import java.util.Objects;

public class CartId {

	private final String cartId;
	
	public CartId(String cartId) {
		this.cartId = cartId;
	}
	
	public String getCartId() {
		return cartId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartId other = (CartId) obj;
		return Objects.equals(cartId, other.cartId);
	}
}
