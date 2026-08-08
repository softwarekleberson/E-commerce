package com.cleancode.ecommerce.promotional.domain;

import java.util.Objects;
import java.util.UUID;

public class VoucherId {

	private final String voucherId;

	public VoucherId(String voucherId) {
		this.voucherId = Objects.requireNonNull(voucherId);
	}
	
	public VoucherId () {
		this.voucherId = UUID.randomUUID().toString();
	}
	
	public String getVoucherId() {
		return voucherId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(voucherId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VoucherId other = (VoucherId) obj;
		return Objects.equals(voucherId, other.voucherId);
	}
}
