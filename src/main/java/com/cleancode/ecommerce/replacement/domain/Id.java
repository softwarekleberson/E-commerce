package com.cleancode.ecommerce.replacement.domain;

import java.util.Objects;
import java.util.UUID;

public class Id {

	private final String id;

	public Id(String id) {
		Objects.requireNonNull(id, "Replacement ID cannot be null");
		this.id = id;
	}
	
	public Id() {
		this.id = UUID.randomUUID().toString();
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
		Id other = (Id) obj;
		return Objects.equals(id, other.id);
	}
}