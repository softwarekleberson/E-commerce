package com.cleancode.ecommerce.payment.domain;

import java.math.BigDecimal;
import java.util.Objects;

import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public class Total {

	private final BigDecimal value;
	private final TypeCoin typeCoin;
	
	public Total(BigDecimal value, TypeCoin typeCoin) {

        if (value == null) {
            throw new IllegalDomainPayment("Value cannot be null");
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalDomainPayment("Value must be greater than zero");
        }

        if (typeCoin == null) {
            throw new IllegalDomainPayment("Currency type is required");
        }

        this.value = value;
        this.typeCoin = typeCoin;
    }
	
	public BigDecimal getTotalValue() {
		return value;
	}
	
	public TypeCoin getTypeCoin() {
		return typeCoin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, typeCoin);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Total other = (Total) obj;
		return Objects.equals(value, other.value) && typeCoin == other.typeCoin;
	}
}