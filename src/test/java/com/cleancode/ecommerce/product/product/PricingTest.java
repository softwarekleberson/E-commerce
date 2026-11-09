package com.cleancode.ecommerce.product.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.exception.IllegalPricingException;

class PricingTest {

	@Test
	void shouldAcceptPricingGreaterThanZeroAndAtMostOne() {
		assertThat(new Pricing(new BigDecimal("0.01")).getPricing()).isEqualByComparingTo("0.01");
		assertThat(new Pricing(BigDecimal.ONE).getPricing()).isEqualByComparingTo(BigDecimal.ONE);
	}

	@Test
	void shouldRejectPricingOfZero() {
		assertThatThrownBy(() -> new Pricing(BigDecimal.ZERO))
				.isInstanceOf(IllegalPricingException.class)
				.hasMessage("Pricing must be between 0 and 1 inclusive");
	}

	@Test
	void shouldRejectPricingAboveOne() {
		assertThatThrownBy(() -> new Pricing(new BigDecimal("1.01")))
				.isInstanceOf(IllegalPricingException.class)
				.hasMessage("Pricing must be between 0 and 1 inclusive");
	}
}
