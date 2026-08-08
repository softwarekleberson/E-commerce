package com.cleancode.ecommerce.stock.productInput;

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
    @ValueSource(ints = {1, 5, 10, 100, 1000})
    @DisplayName("Should allow instantiating Quantity with integer values ​​strictly greater than zero")    void shouldCreateQuantityWithValidValues(int validValue) {
        Quantity quantity = new Quantity(validValue);

        assertThat(quantity).isNotNull();
        assertThat(quantity.getQuantity()).isEqualTo(validValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -50})
    @DisplayName("Should throw an exception when the provided quantity is less than or equal to zero")    void shouldThrowExceptionWhenQuantityIsInvalid(int invalidValue) {
        assertThatThrownBy(() -> new Quantity(invalidValue))
                .isInstanceOf(IllegalStockException.class)
                .hasMessageContaining("quantity must have a value greater than 0");
    }

    @Test
    @DisplayName("Should strictly validate the equality contract (equals and hashCode) based on the primitive value")    void shouldRespectEqualsAndHashCodeContract() {
        Quantity q1 = new Quantity(10);
        Quantity q2 = new Quantity(10);
        Quantity qDifferent = new Quantity(20);

        // Quantidades iguais geram objetos logicamente iguais
        assertThat(q1).isEqualTo(q2);
        assertThat(q1.hashCode()).isEqualTo(q2.hashCode());

        // Comparações que devem falhar
        assertThat(q1).isNotEqualTo(qDifferent);
        assertThat(q1).isNotEqualTo(null);
        assertThat(q1).isNotEqualTo(10); // O tipo primitivo Puro não é igual ao Value Object Quantity
    }
}