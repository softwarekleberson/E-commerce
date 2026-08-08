package com.cleancode.ecommerce.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.user.domain.UserId;

class UserIdTest {

    @Test
    @DisplayName("Should create a UserId with the provided string value")
    void shouldCreateWithProvidedId() {
        String expectedId = "user-123";
        
        UserId userId = new UserId(expectedId);
        
        assertThat(userId.getUserId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Should generate a valid UUID when using the default constructor")
    void shouldGenerateUuidAutomatically() {
        UserId userId = new UserId();
        
        assertThat(userId.getUserId()).isNotNull();
        // Validates that the generated string adheres to a true UUID format
        assertThatCode(() -> UUID.fromString(userId.getUserId())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should throw NullPointerException when passing null to the constructor")
    void shouldThrowExceptionForNullValue() {
        assertThatThrownBy(() -> new UserId(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should ensure structural equality (equals and hashCode) for identical IDs")
    void shouldRespectEqualsAndHashCodeContract() {
        String commonId = "same-id";
        UserId userId1 = new UserId(commonId);
        UserId userId2 = new UserId(commonId);
        UserId differentUserId = new UserId("different-id");

        // Equals verification
        assertThat(userId1).isEqualTo(userId2);
        assertThat(userId1).isNotEqualTo(differentUserId);
        assertThat(userId1).isNotEqualTo(null);

        // HashCode verification
        assertThat(userId1.hashCode()).isEqualTo(userId2.hashCode());
        assertThat(userId1.hashCode()).isNotEqualTo(differentUserId.hashCode());
    }
}