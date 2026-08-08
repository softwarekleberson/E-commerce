package com.cleancode.ecommerce.promotional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.adm.domain.exception.IllegalAdmException;
import com.cleancode.ecommerce.promotional.domain.Discount;

class DiscountTest {

    @ParameterizedTest
    @ValueSource(strings = {"0.0", "0.00", "1.0", "15.50", "100"})
    @DisplayName("Should allow creating Discount with values greater than or equal to zero")
    void shouldCreateDiscountWithValidValues(String validValue) {
        BigDecimal value = new BigDecimal(validValue);
        Discount discount = new Discount(value);

        assertThat(discount).isNotNull();
        assertThat(discount.getDiscount()).isEqualByComparingTo(value);
    }

    @Test
    @DisplayName("Should throw exception when discount value is less than zero")
    void shouldThrowExceptionWhenDiscountIsNegative() {
        BigDecimal negativeValue = new BigDecimal("-0.01");

        assertThatThrownBy(() -> new Discount(negativeValue))
                .isInstanceOf(IllegalAdmException.class)
                .hasMessageContaining("Discount must be greater than 1");
    }

    @Test
    @DisplayName("Should throw NullPointerException if the provided BigDecimal is null")
    void shouldThrowNpeWhenValueIsNull() {
        assertThatThrownBy(() -> new Discount(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Note on equality contract: Current equals implementation fails for different instances with the same value")
    void equalityNote() {
        BigDecimal val1 = new BigDecimal("10.0");
        BigDecimal val2 = new BigDecimal("10.0");

        Discount d1 = new Discount(val1);
        Discount d2 = new Discount(val2);

        // Como a classe usa d1.discount == d2.discount, isso dará falso no Java
        assertThat(d1).isNotEqualTo(d2); 
    }
}