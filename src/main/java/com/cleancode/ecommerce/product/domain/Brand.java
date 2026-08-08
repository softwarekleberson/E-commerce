package com.cleancode.ecommerce.product.domain;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Brand {

	private final String brand;
	
	public Brand(String brand) {
		if(brand == null || brand.trim().isEmpty()) {
			throw new IllegalDomainException("Brand not be null");
		}
		this.brand = brand;
	}
	
	public String getBrand() {
		return brand;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brand);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Brand other = (Brand) obj;
		return Objects.equals(brand, other.brand);
	}
}
