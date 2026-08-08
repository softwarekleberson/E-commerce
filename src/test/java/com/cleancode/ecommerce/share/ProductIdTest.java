package com.cleancode.ecommerce.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.ProductId;

class ProductIdTest {

    @Test
    @DisplayName("Should create a ProductId with the exact provided string value")
    void shouldCreateWithProvidedId() {
        String expectedId = "prod-789";
        
        ProductId productId = new ProductId(expectedId);
        
        assertThat(productId.getProductId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Should generate a valid random UUID when using the default constructor")
    void shouldGenerateUuidAutomatically() {
        ProductId productId = new ProductId();
        
        assertThat(productId.getProductId()).isNotNull();
        // Validates that the generated fallback string is a structured UUID format
        assertThatCode(() -> UUID.fromString(productId.getProductId())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should allow and hold null value when explicit constructor receives a null reference")
    void shouldAllowNullValueWithoutThrowingException() {
        ProductId productId = new ProductId(null);
        
        assertThat(productId.getProductId()).isNull();
    }

    @Test
    @DisplayName("Should ensure identity equality (equals and hashCode) matches for identical inner strings")
    void shouldRespectEqualsAndHashCodeContract() {
        String baseId = "product-unique-123";
        ProductId id1 = new ProductId(baseId);
        ProductId id2 = new ProductId(baseId);
        ProductId differentId = new ProductId("product-unique-456");

        // Equals validations
        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(differentId);
        assertThat(id1).isNotEqualTo(null);

        // HashCode validations
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
        assertThat(id1.hashCode()).isNotEqualTo(differentId.hashCode());
    }
}