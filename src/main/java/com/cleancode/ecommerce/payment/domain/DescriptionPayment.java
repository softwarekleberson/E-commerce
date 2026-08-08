package com.cleancode.ecommerce.payment.domain;

import java.util.Objects;

public class DescriptionPayment {

	private final TypePayment typePayment;
	
	public DescriptionPayment(TypePayment typePayment) {
		this.typePayment = typePayment;
	}
	
	public TypePayment getTypePayment() {
		return typePayment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(typePayment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DescriptionPayment other = (DescriptionPayment) obj;
		return typePayment == other.typePayment;
	}
}
