package com.cleancode.ecommerce.product.domain.books;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Edition {

	private final String edition;
	
	public Edition(String edition) {
		if(edition == null || edition.trim().isEmpty()) {
			throw new IllegalDomainException("Edition not be null");
		}
		this.edition = edition;
	}
	
	public String getEdition() {
		return edition;
	}

	@Override
	public int hashCode() {
		return Objects.hash(edition);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edition other = (Edition) obj;
		return Objects.equals(edition, other.edition);
	}
}
