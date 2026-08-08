package com.cleancode.ecommerce.product.domain;

import java.util.Objects;

import com.cleancode.ecommerce.product.domain.exception.IllegalDimensionException;

public class Dimension {

	public final int NUMBER_MIM_DIMENSION = 0;
	private final double height;
	private final double width;
	private final double length;
	private final double weight;

	public Dimension(double height, double width, double length, double weight) {
		if (height < NUMBER_MIM_DIMENSION) {
			throw new IllegalDimensionException("Number page not be less than 0");
		}

		if (width < NUMBER_MIM_DIMENSION) {
			throw new IllegalDimensionException("Width page not be less than 0");
		}

		if (length < NUMBER_MIM_DIMENSION) {
			throw new IllegalDimensionException("Length page not be less than 0");
		}

		if (weight < NUMBER_MIM_DIMENSION) {
			throw new IllegalDimensionException("Weight page not be less than 0");
		}

		this.height = height;
		this.width = width;
		this.length = length;
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getLength() {
		return length;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public int hashCode() {
		return Objects.hash(height, length, weight, width);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dimension other = (Dimension) obj;
		return Double.doubleToLongBits(height) == Double.doubleToLongBits(other.height)
				&& Double.doubleToLongBits(length) == Double.doubleToLongBits(other.length)
				&& Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight)
				&& Double.doubleToLongBits(width) == Double.doubleToLongBits(other.width);
	}
}