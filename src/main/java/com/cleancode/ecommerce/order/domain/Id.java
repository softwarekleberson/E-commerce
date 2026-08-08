package com.cleancode.ecommerce.order.domain;

import java.util.Objects;
import java.util.UUID;

import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class Id {

	private String id;
	
	public Id() {
		this.id = UUID.randomUUID().toString();
	}
	
	public Id(String id) {
		if(id == null) throw new IllegalDomainOrder("Id delivery is required");
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
		Id other = (Id) obj;
		return Objects.equals(id, other.id);
	}
}
