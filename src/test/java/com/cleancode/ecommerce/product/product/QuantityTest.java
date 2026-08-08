package com.cleancode.ecommerce.product.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.exception.IllegalStockException;

class QuantityTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 100, 10_000})
    @DisplayName("Should successfully create Quantity instance for values strictly greater than 0")
    void shouldCreateQuantityForValidValues(int validAmount) {
        Quantity quantity = new Quantity(validAmount);
        assertThat(quantity.getQuantity()).isEqualTo(validAmount);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @DisplayName("Should throw IllegalStockException when input value is less than or equal to 0")
    void shouldThrowExceptionForValuesLessThanOrEqualToZero(int invalidAmount) {
        assertThatThrownBy(() -> new Quantity(invalidAmount))
                .isInstanceOf(IllegalStockException.class)
                .hasMessage("quantity must have a value greater than 0");
    }

    @Test
    @DisplayName("Should ensure value equality (equals and hashCode) matches for identical integers")
    void shouldRespectEqualsAndHashCodeContract() {
        Quantity quantity1 = new Quantity(10);
        Quantity quantity2 = new Quantity(10);
        Quantity differentQuantity = new Quantity(25);

        // Equals checks
        assertThat(quantity1).isEqualTo(quantity2);
        assertThat(quantity1).isNotEqualTo(differentQuantity);
        assertThat(quantity1).isNotEqualTo(null);

        // HashCode checks
        assertThat(quantity1.hashCode()).isEqualTo(quantity2.hashCode());
        assertThat(quantity1.hashCode()).isNotEqualTo(differentQuantity.hashCode());
    }
}