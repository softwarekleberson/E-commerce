package com.cleancode.ecommerce.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.cart.domain.CartItemId;

class CartItemIdTest {

    @Test
    @DisplayName("Should create a CartItemId with the exact provided string value")
    void shouldCreateWithProvidedId() {
        String customId = "item-999";
        
        CartItemId cartItemId = new CartItemId(customId);
        
        assertThat(cartItemId.getCartItemId()).isEqualTo(customId);
    }

    @Test
    @DisplayName("Should generate a valid random UUID when using the default constructor")
    void shouldGenerateUuidAutomatically() {
        CartItemId cartItemId = new CartItemId();
        
        assertThat(cartItemId.getCartItemId()).isNotNull();
        // Structural assertion to guarantee the generated string is a true UUID format
        assertThatCode(() -> UUID.fromString(cartItemId.getCartItemId())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should allow and retain null values when explicit string constructor receives null")
    void shouldAllowNullValueWithoutThrowingException() {
        CartItemId cartItemId = new CartItemId(null);
        
        assertThat(cartItemId.getCartItemId()).isNull();
    }

    @Test
    @DisplayName("Should ensure structural equality (equals and hashCode) for matching IDs")
    void shouldRespectEqualsAndHashCodeContract() {
        String commonId = "cart-item-id-123";
        CartItemId id1 = new CartItemId(commonId);
        CartItemId id2 = new CartItemId(commonId);
        CartItemId differentId = new CartItemId("cart-item-id-456");

        // Equals validations
        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(differentId);
        assertThat(id1).isNotEqualTo(null);

        // HashCode validations
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
        assertThat(id1.hashCode()).isNotEqualTo(differentId.hashCode());
    }

    @Test
    @DisplayName("Should produce correct string representation from toString")
    void shouldReturnCorrectToStringOutput() {
        CartItemId cartItemId = new CartItemId("item-abc");
        
        assertThat(cartItemId.toString()).isEqualTo("CartItemId [cartItemId=item-abc]");
    }
}