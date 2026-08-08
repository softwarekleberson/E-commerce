package com.cleancode.ecommerce.product.domain.bag;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Color {

	private String color;
	
	public Color(String color) {
		if(color == null || color.trim().isBlank()) {
			throw new IllegalDomainException("Color not be null");
		}
		
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		return Objects.equals(color, other.color);
	}
}