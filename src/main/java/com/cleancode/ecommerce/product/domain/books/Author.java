package com.cleancode.ecommerce.product.domain.books;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Author {

	private final String author;

	public Author(String author) {
		if (author == null || author.trim().isEmpty()) {
			throw new IllegalDomainException("Author not ne null");
		}
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return Objects.equals(author, other.author);
	}
}
