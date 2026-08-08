package com.cleancode.ecommerce.product.domain;

import java.math.BigDecimal;
import java.util.Objects;

import com.cleancode.ecommerce.product.domain.exception.IllegalPricingException;

public class Pricing {

	private BigDecimal pricing;

	public Pricing(BigDecimal pricing) {
		if (pricing == null) {
			throw new IllegalPricingException("Pricing cannot be null");
		}
		
		if (pricing.compareTo(BigDecimal.ZERO) < 0 || pricing.compareTo(BigDecimal.ONE) > 0) {
		    throw new IllegalPricingException("Pricing must be between 0 and 1 inclusive");
		}

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