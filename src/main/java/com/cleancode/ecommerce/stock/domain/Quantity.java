package com.cleancode.ecommerce.stock.domain;

import java.util.Objects;

import com.cleancode.ecommerce.stock.domain.exception.IllegalStockException;

public class Quantity {

	public static final int MIN_QUANTITY = 0;
	private int quantity;

	public Quantity(int quantity) {
		if(quantity <= MIN_QUANTITY) {
			throw new IllegalStockException("quantity must have a value greater than 0");
		}
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
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
