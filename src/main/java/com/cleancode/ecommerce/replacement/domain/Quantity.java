package com.cleancode.ecommerce.replacement.domain;

import java.util.Objects;

import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class Quantity {

	private final static int LESS_VALUE = 1;
	private final int quantity;

	public Quantity(int quantity) {
		if(quantity < LESS_VALUE) {
			throw new IllegalDomainOrder("Value cannot be less than 0");
		}
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public static int getLessValue() {
		return LESS_VALUE;
	}

	@Override
	public int hashCode() {
		return Objects.hash(quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quantity other = (Quantity) obj;
		return quantity == other.quantity;
	}
}