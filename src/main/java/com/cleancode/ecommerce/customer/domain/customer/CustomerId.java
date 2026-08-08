package com.cleancode.ecommerce.customer.domain.customer;

import java.util.Objects;

public class CustomerId {

	private final String value;
	
	public CustomerId(String value) {
	     if (value == null) throw new IllegalArgumentException("id cannot be null");
	     this.value = value;
	}

	public String getValue() {
	      return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerId other = (CustomerId) obj;
		return Objects.equals(value, other.value);
	}
}
