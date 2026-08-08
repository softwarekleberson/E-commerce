package com.cleancode.ecommerce.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.stock.domain.Supplier;

class SupplierTest {

    @Test
    @DisplayName("Must instantiate a Supplier successfully when the given name is valid")    void shouldCreateSupplierSuccessfully() {
        String validSupplierName = "Distribuidora de Tecnologia LTDA";
        
        Supplier supplier = new Supplier(validSupplierName);

        assertThat(supplier).isNotNull();
        assertThat(supplier.getSupplier()).isEqualTo(validSupplierName);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", "\n", "\t"})
    @DisplayName("Should throw an exception when the supplier name is null, empty, or consists only of spaces")    void shouldThrowExceptionWhenSupplierIsInvalid(String invalidSupplier) {
        assertThatThrownBy(() -> new Supplier(invalidSupplier))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessageContaining("supplier needs be information");
    }

    @Test
    @DisplayName("Should validate the equality contract (equals and hashCode) based strictly on the supplier name")    void shouldRespectEqualsAndHashCodeContract() {
        Supplier s1 = new Supplier("Fornecedor A");
        Supplier s2 = new Supplier("Fornecedor A");
        Supplier sDifferent = new Supplier("Fornecedor B");

        // Nomes idênticos geram objetos logicamente iguais
        assertThat(s1).isEqualTo(s2);
        assertThat(s1.hashCode()).isEqualTo(s2.hashCode());

        // Instâncias com nomes ou tipos diferentes devem falhar na igualdade
        assertThat(s1).isNotEqualTo(sDifferent);
        assertThat(s1).isNotEqualTo(null);
        assertThat(s1).isNotEqualTo("Fornecedor A"); // String pura não é idêntica ao Value Object Supplier
    }
}