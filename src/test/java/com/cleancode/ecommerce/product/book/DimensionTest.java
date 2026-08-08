package com.cleancode.ecommerce.product.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.Dimension;

public class DimensionTest {

	@Test
	@DisplayName("Should create a valid dimension")
	void shouldCreateValidDimension() {
		Dimension dimension = new Dimension(10.0, 5.0, 15.0, 2.5);

		assertEquals(10.0, dimension.getHeight());
		assertEquals(5.0, dimension.getWidth());
		assertEquals(15.0, dimension.getLength());
		assertEquals(2.5, dimension.getWeight());
	}

	@Test
	@DisplayName("Should be equal when all values are the same")
	void shouldBeEqualWhenAllValuesAreSame() {
		Dimension d1 = new Dimension(10.0, 5.0, 15.0, 2.5);
		Dimension d2 = new Dimension(10.0, 5.0, 15.0, 2.5);

		assertEquals(d1, d2);
		assertEquals(d1.hashCode(), d2.hashCode());
	}

	@Test
	@DisplayName("Should not be equal when any value is different")
	void shouldNotBeEqualWhenValuesAreDifferent() {
		Dimension d1 = new Dimension(10.0, 5.0, 15.0, 2.5);
		Dimension d2 = new Dimension(10.0, 5.0, 15.0, 3.0);

		assertNotEquals(d1, d2);
	}

	@Test
	@DisplayName("Should return true when comparing to itself")
	void shouldBeEqualToItself() {
		Dimension dimension = new Dimension(8.0, 6.0, 12.0, 1.0);
		assertEquals(dimension, dimension);
	}

	@Test
	@DisplayName("Should return false when comparing to null")
	void shouldNotBeEqualToNull() {
		Dimension dimension = new Dimension(8.0, 6.0, 12.0, 1.0);
		assertNotEquals(null, dimension);
	}

	@Test
	@DisplayName("Should return false when comparing to different class")
	void shouldNotBeEqualToDifferentClass() {
		Dimension dimension = new Dimension(8.0, 6.0, 12.0, 1.0);
		Object obj = new Object();
		assertNotEquals(dimension, obj);
	}
}
