package com.cleancode.ecommerce.stock.domain;

import java.math.BigDecimal;
import java.util.Objects;

import com.cleancode.ecommerce.stock.domain.exception.IllegalStockException;

public class Purchaseprice {

	private BigDecimal purchasePrice;

	public Purchaseprice(BigDecimal purchasePrice) {
		if (purchasePrice.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalStockException("the value purchase price need positive");
		}

		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(purchasePrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Purchaseprice other = (Purchaseprice) obj;
		return Objects.equals(purchasePrice, other.purchasePrice);
	}
}