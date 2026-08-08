package com.cleancode.ecommerce.stock.domain;

import java.util.Objects;
import java.util.UUID;

public class StockId {

	private final String stockId;

	public StockId() {
		this.stockId = UUID.randomUUID().toString();
	}

	public StockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStockId() {
		return stockId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stockId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockId other = (StockId) obj;
		return Objects.equals(stockId, other.stockId);
	}
}