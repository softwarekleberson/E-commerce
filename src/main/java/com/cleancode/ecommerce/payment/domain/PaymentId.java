package com.cleancode.ecommerce.payment.domain;

import java.util.Objects;
import java.util.UUID;

public class PaymentId {

	private final String id;
	
	public PaymentId() {
		id = UUID.randomUUID().toString();
	}
	
	public PaymentId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentId other = (PaymentId) obj;
		return Objects.equals(id, other.id);
	}
}