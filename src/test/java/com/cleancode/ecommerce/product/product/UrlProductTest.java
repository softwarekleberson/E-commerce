package com.cleancode.ecommerce.product.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.shared.kernel.UrlProduct;

class UrlProductTest {

    @Test
    @DisplayName("Should create an instance and preserve the exact provided URL string")
    void shouldCreateUrlProductWithProvidedString() {
        String testUrl = "https://cdn.cleancode.com/images/products/item-01.png";

        UrlProduct urlProduct = new UrlProduct(testUrl);

        assertThat(urlProduct.getUrlProduct()).isEqualTo(testUrl);
    }

    @Test
    @DisplayName("Should allow and retain a null value without throwing an exception")
    void shouldAllowNullValueWithoutThrowingException() {
        UrlProduct urlProduct = new UrlProduct(null);

        assertThat(urlProduct.getUrlProduct()).isNull();
    }

    @Test
    @DisplayName("Should ensure equality (equals and hashCode) matches for identical URL values")
    void shouldRespectEqualsAndHashCodeContract() {
        String baseUrl = "https://cleancode.com/product.jpg";
        UrlProduct urlProduct1 = new UrlProduct(baseUrl);
        UrlProduct urlProduct2 = new UrlProduct(baseUrl);
        UrlProduct differentUrlProduct = new UrlProduct("https://cleancode.com/other.jpg");

        // Equals checks
        assertThat(urlProduct1).isEqualTo(urlProduct2);
        assertThat(urlProduct1).isNotEqualTo(differentUrlProduct);
        assertThat(urlProduct1).isNotEqualTo(null);

        // HashCode checks
        assertThat(urlProduct1.hashCode()).isEqualTo(urlProduct2.hashCode());
        assertThat(urlProduct1.hashCode()).isNotEqualTo(differentUrlProduct.hashCode());
    }
}