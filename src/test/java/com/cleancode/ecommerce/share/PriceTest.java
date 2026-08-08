package com.cleancode.ecommerce.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

class PriceTest {

    @Test
    @DisplayName("Should create Price successfully when value is zero or positive")
    void shouldCreatePriceWithValidValues() {
        Price zeroPrice = new Price(BigDecimal.ZERO, TypeCoin.DOLAR);
        Price positivePrice = new Price(new BigDecimal("99.99"), TypeCoin.REAL);

        assertThat(zeroPrice.getPrice()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(zeroPrice.getCoin()).isEqualTo(TypeCoin.DOLAR);
        
        assertThat(positivePrice.getPrice()).isEqualByComparingTo("99.99");
        assertThat(positivePrice.getCoin()).isEqualTo(TypeCoin.REAL);
    }

    @Test
    @DisplayName("Should create Price using single-argument constructor successfully")
    void shouldCreatePriceWithSingleArgumentConstructor() {
        Price price = new Price(new BigDecimal("49.50"));
        
        assertThat(price.getPrice()).isEqualByComparingTo("49.50");
        assertThat(price.getCoin()).isNull();
    }

    @Test
    @DisplayName("Should throw IllegalDomainException when price value is null in the two-argument constructor")
    void shouldThrowExceptionWhenPriceIsNull() {
        assertThatThrownBy(() -> new Price(null, TypeCoin.DOLAR))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Price not be null");
    }

    @Test
    @DisplayName("Should throw NullPointerException when single-argument constructor receives null due to immediate compareTo invocation")
    void shouldThrowNpeWhenSingleArgConstructorReceivesNull() {
        assertThatThrownBy(() -> new Price(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw IllegalDomainException when value is negative")
    void shouldThrowExceptionWhenPriceIsNegative() {
        BigDecimal negativeValue = new BigDecimal("-0.01");

        assertThatThrownBy(() -> new Price(negativeValue, TypeCoin.DOLAR))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Price must not be less than or equal to 0");

        assertThatThrownBy(() -> new Price(negativeValue))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Price must not be less than or equal to 0");
    }

    @Test
    @DisplayName("Should calculate sale price with margin applied using static factory method")
    void shouldCalculateSalePriceWithinMarginPolicy() {
        BigDecimal highestPurchasePrice = new BigDecimal("100.00");
        BigDecimal markupPricingMargin = new BigDecimal("0.20"); // 20% markup margin

        // Formula check: 100 * (1 + 0.20) = 120.00
        Price generatedPrice = Price.salePriceWithinMarginPolicy(markupPricingMargin, highestPurchasePrice, TypeCoin.EURO);

        assertThat(generatedPrice.getPrice()).isEqualByComparingTo("120.00");
        assertThat(generatedPrice.getCoin()).isEqualTo(TypeCoin.EURO);
    }

    @Test
    @DisplayName("Should create updated price when price is higher than current selling price")
    void shouldCreatePriceWhenAboveMarginProfit() {
        BigDecimal currentPrice = new BigDecimal("50.00");
        BigDecimal newHigherPrice = new BigDecimal("55.00");

        Price validationResult = Price.PriceAboveMarginProfit(currentPrice, newHigherPrice, TypeCoin.LIBRA);

        assertThat(validationResult.getPrice()).isEqualByComparingTo("55.00");
    }

    @Test
    @DisplayName("Should throw IllegalDomainException when new price is lower than or equal to current price")
    void shouldRejectPriceWhenNotAboveMarginProfit() {
        BigDecimal currentPrice = new BigDecimal("50.00");
        BigDecimal equalPrice = new BigDecimal("50.00");
        BigDecimal lowerPrice = new BigDecimal("49.99");

        assertThatThrownBy(() -> Price.PriceAboveMarginProfit(currentPrice, equalPrice, TypeCoin.DOLAR))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("New price must be higher than current selling price, respecting the pricing policy");

        assertThatThrownBy(() -> Price.PriceAboveMarginProfit(currentPrice, lowerPrice, TypeCoin.DOLAR))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("New price must be higher than current selling price, respecting the pricing policy");
    }

    @Test
    @DisplayName("Should ensure value equality (equals and hashCode) accounts for both numerical values and currencies")
    void shouldRespectEqualsAndHashCodeContract() {
        Price price1 = new Price(new BigDecimal("10.0"), TypeCoin.DOLAR);
        Price price2 = new Price(new BigDecimal("10.0"), TypeCoin.DOLAR);
        Price priceDifferentCoin = new Price(new BigDecimal("10.0"), TypeCoin.REAL);
        Price priceDifferentValue = new Price(new BigDecimal("20.0"), TypeCoin.DOLAR);

        // Equals validations
        assertThat(price1).isEqualTo(price2);
        assertThat(price1).isNotEqualTo(priceDifferentCoin);
        assertThat(price1).isNotEqualTo(priceDifferentValue);
        assertThat(price1).isNotEqualTo(null);

        // HashCode validations
        assertThat(price1.hashCode()).isEqualTo(price2.hashCode());
        assertThat(price1.hashCode()).isNotEqualTo(priceDifferentCoin.hashCode());
    }
}