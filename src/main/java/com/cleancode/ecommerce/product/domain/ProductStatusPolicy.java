package com.cleancode.ecommerce.product.domain;

import java.util.Objects;

public class ProductStatusPolicy {

	private String justification;
	private ProductStatusCategory category;

	public ProductStatusPolicy(String justification, ProductStatusCategory category) {
		this.justification = justification;
		this.category = category;
	}

	public ProductStatusPolicy(ProductStatusCategory category) {
		this.category = category;
	}

	public static ProductStatusPolicy manualDeactivation(String justification, ProductStatusCategory category) {
		return new ProductStatusPolicy(justification, category);
	}

	public static ProductStatusPolicy automaticDeactivation() {
		return new ProductStatusPolicy(ProductStatusCategory.OUT_OF_MARKET);
	}

	public static ProductStatusPolicy activation(String justification, ProductStatusCategory category) {
		return new ProductStatusPolicy(justification, category);
	}

	public String getJustification() {
		return justification;
	}

	public ProductStatusCategory getCategory() {
		return category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, justification);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductStatusPolicy other = (ProductStatusPolicy) obj;
		return category == other.category && Objects.equals(justification, other.justification);
	}
}