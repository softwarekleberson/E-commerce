package com.cleancode.ecommerce.product.domain.books;

import java.math.BigDecimal;
import java.util.Objects;

public class Pricing {

	private final BigDecimal pricing;
	
	public Pricing(BigDecimal pricing) {
		this.pricing = pricing;
	}
	
	public BigDecimal getPricing() {
		return pricing;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pricing);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pricing other = (Pricing) obj;
		return Objects.equals(pricing, other.pricing);
	}
}
