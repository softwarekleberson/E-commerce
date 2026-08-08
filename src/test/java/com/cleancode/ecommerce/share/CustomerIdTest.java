package com.cleancode.ecommerce.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.customer.CustomerId;

class CustomerIdTest {

    @Test
    @DisplayName("Should construct CustomerId successfully when value is valid")
    void shouldConstructCustomerIdSuccessfully() {
        CustomerId customerId = new CustomerId("cust-12345");

        assertThat(customerId.getValue()).isEqualTo("cust-12345");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when ID value is null")
    void shouldThrowExceptionWhenValueIsNull() {
        assertThatThrownBy(() -> new CustomerId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id cannot be null");
    }

    @Test
    @DisplayName("Should guarantee equals and hashCode contract based on encapsulated value")
    void shouldRespectEqualsAndHashCodeContract() {
        CustomerId id1 = new CustomerId("abc-123");
        CustomerId id2 = new CustomerId("abc-123");
        CustomerId differentId = new CustomerId("xyz-999");

        // Verificações do método equals
        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(differentId);
        assertThat(id1).isNotEqualTo(null);
        assertThat(id1).isNotEqualTo("apenas uma string");

        // Verificações do método hashCode
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
        assertThat(id1.hashCode()).isNotEqualTo(differentId.hashCode());
    }
}