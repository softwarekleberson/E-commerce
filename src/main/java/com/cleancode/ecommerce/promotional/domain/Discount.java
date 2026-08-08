package com.cleancode.ecommerce.promotional.domain;

import java.math.BigDecimal;
import java.util.Objects;

import com.cleancode.ecommerce.adm.domain.exception.IllegalAdmException;

public class Discount {
	
	private static final BigDecimal LESS_VALUE_DISCOUNT = BigDecimal.ZERO;
	private final BigDecimal discount;

	public Discount(BigDecimal discount) {
		if (discount.compareTo(LESS_VALUE_DISCOUNT) < 0) {
			throw new IllegalAdmException("Discount must be greater than 1");
		}
		this.discount = discount;
	}
	
	public BigDecimal getDiscount() {
		return discount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(discount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Discount other = (Discount) obj;
		return discount == other.discount;
	}
}
