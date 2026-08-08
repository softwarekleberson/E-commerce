package com.cleancode.ecommerce.stock.domain;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Supplier {

	private String supplier;

	public Supplier(String supplier) {
		if (supplier == null || supplier.isBlank()) {
			throw new IllegalDomainException("supplier needs be information");
		}
		this.supplier = supplier;
	}

	public String getSupplier() {
		return supplier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(supplier);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		return Objects.equals(supplier, other.supplier);
	}
}
