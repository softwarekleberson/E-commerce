package com.cleancode.ecommerce.product.domain.books;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Synopsis {

	private final String synopsis;
	
	public Synopsis(String synopsis) {
		if(synopsis == null || synopsis.trim().isEmpty()) {
			throw new IllegalDomainException("Synopsis not be null");
		}
		
		this.synopsis = synopsis;
	}
	
	public String getSynopsis() {
		return synopsis;
	}

	@Override
	public int hashCode() {
		return Objects.hash(synopsis);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Synopsis other = (Synopsis) obj;
		return Objects.equals(synopsis, other.synopsis);
	}
}
