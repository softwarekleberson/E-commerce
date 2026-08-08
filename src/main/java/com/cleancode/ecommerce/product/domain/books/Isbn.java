package com.cleancode.ecommerce.product.domain.books;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Isbn {

	private final String isbn;

	public Isbn(String isbn) {
		String ISBN_REGEX = "^(?:\\d{9}X|\\d{10}|97[89]\\d{10})$";
		if (!isbn.matches(ISBN_REGEX)) {
			throw new IllegalDomainException("Isbn not be valid");
		}

		this.isbn = isbn;
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Isbn other = (Isbn) obj;
		return Objects.equals(isbn, other.isbn);
	}
}