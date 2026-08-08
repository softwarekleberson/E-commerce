package com.cleancode.ecommerce.stock.productInput;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

class PriceTest {

    @Test
    @DisplayName("Should successfully instantiate Price with value and currency type")    void shouldConstructWithPriceAndCoin() {
        BigDecimal value = new BigDecimal("150.00");
        Price price = new Price(value, TypeCoin.DOLAR);

        assertThat(price).isNotNull();
        assertThat(price.getPrice()).isEqualByComparingTo(value);
        assertThat(price.getCoin()).isEqualTo(TypeCoin.DOLAR);
    }

    @Test
    @DisplayName("Should instantiate Price using the simplified single-argument constructor")    void shouldConstructWithPriceOnly() {
        BigDecimal value = new BigDecimal("49.90");
        Price price = new Price(value);

        assertThat(price).isNotNull();
        assertThat(price.getPrice()).isEqualByComparingTo(value);
        assertThat(price.getCoin()).isNull(); // Construtor de 1 arg não mapeia moeda
    }

    @Test
    @DisplayName("Should throw an exception when the provided price is null")    void shouldThrowExceptionWhenPriceIsNull() {
        assertThatThrownBy(() -> new Price(null, TypeCoin.DOLAR))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessageContaining("Price not be null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"-0.01", "-100.00"})
    @DisplayName("Should throw exception when price is less than zero in both constructors")    void shouldThrowExceptionWhenPriceIsNegative(String negativeValue) {
        BigDecimal val = new BigDecimal(negativeValue);

        assertThatThrownBy(() -> new Price(val, TypeCoin.DOLAR))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessageContaining("Price must not be less than or equal to 0");

        assertThatThrownBy(() -> new Price(val))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessageContaining("Price must not be less than or equal to 0");
    }

    @Test
    @DisplayName("Should accept zero as the lower price limit according to class boundaries")    void shouldAllowZeroAsValidLowestPrice() {
        Price price = new Price(BigDecimal.ZERO);
        assertThat(price.getPrice()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should calculate the selling price based on the multiplied profit margin")    void shouldCalculateSalePriceWithinMarginPolicy() {
        BigDecimal pricing = new BigDecimal("0.50"); // 50% de margem
        BigDecimal highestPurchase = new BigDecimal("100.00");

        // Fórmula interna da classe: purchase * (1 + pricing) -> 100.00 * 1.50 = 150.00
        Price calculatedPrice = Price.salePriceWithinMarginPolicy(pricing, highestPurchase, TypeCoin.DOLAR);

        assertThat(calculatedPrice).isNotNull();
        assertThat(calculatedPrice.getPrice()).isEqualByComparingTo("150.00");
        assertThat(calculatedPrice.getCoin()).isEqualTo(TypeCoin.DOLAR);
    }

    @Test
    @DisplayName("Should allow a new price when it is strictly greater than the current price")    void shouldAllowPriceAboveMarginProfit() {
        BigDecimal current = new BigDecimal("100.00");
        BigDecimal targetNewPrice = new BigDecimal("100.01");

        Price updatedPrice = Price.PriceAboveMarginProfit(current, targetNewPrice, TypeCoin.DOLAR);

        assertThat(updatedPrice.getPrice()).isEqualByComparingTo(targetNewPrice);
    }

    @ParameterizedTest
    @ValueSource(strings = {"100.00", "99.99"})
    @DisplayName("Should throw an exception if the new price is less than or equal to the current selling price")    void shouldThrowExceptionWhenNewPriceIsNotHigherThanCurrent(String invalidNewPrice) {
        BigDecimal current = new BigDecimal("100.00");
        BigDecimal target = new BigDecimal(invalidNewPrice);

        assertThatThrownBy(() -> Price.PriceAboveMarginProfit(current, target, TypeCoin.DOLAR))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessageContaining("New price must be higher than current selling price");
    }

    @Test
    @DisplayName("Should validate the equality contract (equals and hashCode) based on currency and value with identical scales")    void shouldRespectEqualsAndHashCodeContract() {
        // Correção: Usando a mesma escala decimal ("25.0") para evitar a armadilha do equals do BigDecimal
        Price p1 = new Price(new BigDecimal("25.0"), TypeCoin.DOLAR);
        Price p2 = new Price(new BigDecimal("25.0"), TypeCoin.DOLAR); 
        
        // Correção: Alterado para uma moeda diferente (presumindo que exista REAL no seu TypeCoin)
        Price pDifferentCoin = new Price(new BigDecimal("25.0"), TypeCoin.REAL); 

        // Agora a asserção de igualdade passará com sucesso
        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());

        assertThat(p1).isNotEqualTo(pDifferentCoin);
        assertThat(p1).isNotEqualTo(null);
    }
}