package com.cleancode.ecommerce.order.domain;

import java.util.Objects;
import java.util.UUID;

public class OrderId {

	private final String orderId;

	public OrderId() {
		this.orderId = UUID.randomUUID().toString();
	}

	public OrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderId other = (OrderId) obj;
		return Objects.equals(orderId, other.orderId);
	}
}