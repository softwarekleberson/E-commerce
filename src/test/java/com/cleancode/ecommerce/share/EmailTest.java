package com.cleancode.ecommerce.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.customer.domain.customer.exception.IllegalContactException;
import com.cleancode.ecommerce.shared.kernel.Email;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "user@domain.com",
        "user.name+tag@domain.co.uk",
        "123456@domain.org",
        "user_name-minus@sub.domain.com"
    })
    @DisplayName("Should successfully create Email instance for valid formats")
    void shouldCreateEmailForValidFormats(String validEmail) {
        Email emailObj = new Email(validEmail);
        assertThat(emailObj.getEmail()).isEqualTo(validEmail);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "plainaddress",
        "#@%^%#$@#$@#.com",
        "@domain.com",
        "Joe Smith <joe@domain.com>",
        "email.domain.com",
        "email@domain@domain.com"
    })
    @DisplayName("Should throw IllegalContactException for structurally invalid emails")
    void shouldThrowExceptionForInvalidFormats(String invalidEmail) {
        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(IllegalContactException.class)
                .hasMessage("Email is incorrect");
    }

    @Test
    @DisplayName("Should throw IllegalContactException when email string is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(IllegalContactException.class)
                .hasMessage("Email is incorrect");
    }

    @Test
    @DisplayName("Should ensure structural equality (equals and hashCode) for identical emails")
    void shouldRespectEqualsAndHashCodeContract() {
        String baseEmail = "test@domain.com";
        Email email1 = new Email(baseEmail);
        Email email2 = new Email(baseEmail);
        Email differentEmail = new Email("other@domain.com");

        // Equals checks
        assertThat(email1).isEqualTo(email2);
        assertThat(email1).isNotEqualTo(differentEmail);
        assertThat(email1).isNotEqualTo(null);

        // HashCode checks
        assertThat(email1.hashCode()).isEqualTo(email2.hashCode());
        assertThat(email1.hashCode()).isNotEqualTo(differentEmail.hashCode());
    }

    @Test
    @DisplayName("Should produce correct string representation from toString")
    void shouldReturnCorrectToStringOutput() {
        Email email = new Email("info@cleancode.com");
        assertThat(email.toString()).isEqualTo("Email [email=info@cleancode.com]");
    }
}