package com.cleancode.ecommerce.product.domain.books;

import java.time.LocalDate;
import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class PublisherDate {

	private final LocalDate publisherDate;

	public PublisherDate(LocalDate publisherDate) {
		if (publisherDate.isAfter(LocalDate.now())) {
			throw new IllegalDomainException("Date publisher Invalid");
		}

		this.publisherDate = publisherDate;
	}

	public LocalDate getPublisherDate() {
		return publisherDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(publisherDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublisherDate other = (PublisherDate) obj;
		return Objects.equals(publisherDate, other.publisherDate);
	}
}
