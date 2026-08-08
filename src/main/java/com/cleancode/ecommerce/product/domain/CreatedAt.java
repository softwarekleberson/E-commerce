package com.cleancode.ecommerce.product.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreatedAt {

	private final LocalDateTime createdAt;
	
	public CreatedAt() {
		this.createdAt = LocalDateTime.now();
	}
	
	public CreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreatedAt other = (CreatedAt) obj;
		return Objects.equals(createdAt, other.createdAt);
	}
}