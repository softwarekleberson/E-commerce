package com.cleancode.ecommerce.customer.domain.card;

import java.util.Objects;

import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;

public class PrintedName {

	private final String name;

	public PrintedName(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalCardException("Printed name is required in card");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrintedName other = (PrintedName) obj;
		return Objects.equals(name, other.name);
	}
}
