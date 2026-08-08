package com.cleancode.ecommerce.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.stock.domain.Purchaseprice;
import com.cleancode.ecommerce.stock.domain.exception.IllegalStockException;

class PurchasepriceTest {

    @ParameterizedTest
    @ValueSource(strings = {"0.01", "10.50", "100.00", "5000"})
    @DisplayName("Should allow creating PurchasePrice with strictly positive values")    void shouldCreatePurchasePriceWithValidValues(String validValue) {
        BigDecimal value = new BigDecimal(validValue);
        Purchaseprice purchasePrice = new Purchaseprice(value);

        assertThat(purchasePrice).isNotNull();
        assertThat(purchasePrice.getPurchasePrice()).isEqualByComparingTo(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.0", "0.00", "-1.50", "-100"})
    @DisplayName("Should throw an exception when the purchase price is less than or equal to zero")    void shouldThrowExceptionWhenPurchasePriceIsZeroOrNegative(String invalidValue) {
        BigDecimal val = new BigDecimal(invalidValue);

        assertThatThrownBy(() -> new Purchaseprice(val))
                .isInstanceOf(IllegalStockException.class)
                .hasMessageContaining("the value purchase price need positive");
    }

    @Test
    @DisplayName("Should throw NullPointerException if the passed BigDecimal is null")    void shouldThrowNpeWhenValueIsNull() {
        assertThatThrownBy(() -> new Purchaseprice(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should validate the equality contract (equals and hashCode) with identical scales")    void shouldRespectEqualsAndHashCodeContract() {
        BigDecimal val1 = new BigDecimal("45.5");
        BigDecimal val2 = new BigDecimal("45.5");

        Purchaseprice p1 = new Purchaseprice(val1);
        Purchaseprice p2 = new Purchaseprice(val2);
        Purchaseprice pDifferent = new Purchaseprice(new BigDecimal("50.0"));

        // Valores e escalas idênticas geram objetos logicamente iguais
        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());

        // Comparações que devem falhar
        assertThat(p1).isNotEqualTo(pDifferent);
        assertThat(p1).isNotEqualTo(null);
        assertThat(p1).isNotEqualTo("45.5");
    }

    @Test
    @DisplayName("Note on Equals: Failure expected if BigDecimals use different decimal scales")    void shouldDemonstrateScaleSensitivityOnEquality() {
        // A classe usa Objects.equals(purchasePrice, other.purchasePrice), 
        // o que aciona o equals do BigDecimal (que avalia valor e precisão de casas).
        Purchaseprice p1 = new Purchaseprice(new BigDecimal("45.5"));
        Purchaseprice p2 = new Purchaseprice(new BigDecimal("45.50"));

        // O teste garante que eles NÃO são considerados iguais devido a essa característica do Java
        assertThat(p1).isNotEqualTo(p2);
    }
}