package com.cleancode.ecommerce.product.domain.books;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Publisher {

	private final String publisher;
	
	public Publisher(String publisher) {
		if(publisher == null || publisher.trim().isEmpty()) {
			throw new IllegalDomainException("Publisher not be null");
		}
		
		this.publisher = publisher;
	}
	
	public String getPublisher() {
		return publisher;
	}

	@Override
	public int hashCode() {
		return Objects.hash(publisher);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		return Objects.equals(publisher, other.publisher);
	}
}
