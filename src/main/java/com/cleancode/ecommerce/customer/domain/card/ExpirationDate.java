package com.cleancode.ecommerce.customer.domain.card;

import java.time.LocalDate;
import java.util.Objects;

import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;

public class ExpirationDate {

	private LocalDate expirationDate;

	public ExpirationDate(LocalDate expirationDate) {
		if (expirationDate.isBefore(LocalDate.now())) {
			throw new IllegalCardException("Expiration date cannot be in the past");
		}
		this.expirationDate = expirationDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(expirationDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpirationDate other = (ExpirationDate) obj;
		return Objects.equals(expirationDate, other.expirationDate);
	}
}
