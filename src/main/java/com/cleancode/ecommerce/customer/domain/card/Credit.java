package com.cleancode.ecommerce.customer.domain.card;

import java.math.BigDecimal;
import java.util.Objects;

public class Credit {

	private final BigDecimal credit;
	
	public Credit() {
		this.credit = BigDecimal.valueOf(100);
	}
	
	public BigDecimal getCredit() {
		return credit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(credit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credit other = (Credit) obj;
		return Objects.equals(credit, other.credit);
	}
}
