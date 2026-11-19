package com.cleancode.ecommerce.product.domain;

import java.util.Objects;
import java.util.UUID;

public class ProductId {

	private final String productId;

	public ProductId() {
		this.productId = UUID.randomUUID().toString();
	}

	public ProductId(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductId other = (ProductId) obj;
		return Objects.equals(productId, other.productId);
	}
}
