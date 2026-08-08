package com.cleancode.ecommerce.product.domain;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Description {

	private final String description;
	
	public Description(String description) {
		if(description == null || description.trim().isEmpty()) {
			throw new IllegalDomainException("Description not be null");
		}
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Description other = (Description) obj;
		return Objects.equals(description, other.description);
	}
}
