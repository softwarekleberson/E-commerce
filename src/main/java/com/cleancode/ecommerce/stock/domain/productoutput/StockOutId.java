package com.cleancode.ecommerce.stock.domain.productoutput;

import java.util.Objects;

import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;

public class StockOutId {

	private final String stockOutId;
	
	public StockOutId(String stockOutId) {
		if(stockOutId == null) {
			throw new IllegalDomainPayment("Id stock out is required");
		}
		this.stockOutId = stockOutId;
	}
	
	public String getStockOutId() {
		return stockOutId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stockOutId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockOutId other = (StockOutId) obj;
		return Objects.equals(stockOutId, other.stockOutId);
	}
}