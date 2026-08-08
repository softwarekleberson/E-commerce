package com.cleancode.ecommerce.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.shared.exception.IllegalPasswordException;
import com.cleancode.ecommerce.shared.kernel.Password;

class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "P@ssword1",
        "Strong#2026",
        "Valid_Pr0tect!",
        "aB3$ffff" // Exactly 8 characters with all criteria met
    })
    @DisplayName("Should successfully create Password instance when all criteria are met")
    void shouldCreatePasswordWhenValid(String validPassword) {
        Password password = new Password(validPassword);
        assertThat(password.getValue()).isEqualTo(validPassword);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "   ",
        "Short1!" // 7 characters
    })
    @DisplayName("Should throw exception when password is null, blank, or less than 8 characters")
    void shouldRejectInsufficientLengthOrBlank(String weakPassword) {
        assertThatThrownBy(() -> new Password(weakPassword))
                .isInstanceOf(IllegalPasswordException.class)
                .hasMessage("Password must contain at least 8 characters");
    }

    @Test
    @DisplayName("Should throw exception when password string reference is null")
    void shouldRejectNullPassword() {
        assertThatThrownBy(() -> new Password(null))
                .isInstanceOf(IllegalPasswordException.class)
                .hasMessage("Password must contain at least 8 characters");
    }

    @Test
    @DisplayName("Should throw exception when password lacks an uppercase letter")
    void shouldRejectMissingUppercase() {
        assertThatThrownBy(() -> new Password("p@ssword1"))
                .isInstanceOf(IllegalPasswordException.class)
                .hasMessage("Password must contain at least one uppercase letter");
    }

    @Test
    @DisplayName("Should throw exception when password lacks a lowercase letter")
    void shouldRejectMissingLowercase() {
        assertThatThrownBy(() -> new Password("P@SSWORD1"))
                .isInstanceOf(IllegalPasswordException.class)
                .hasMessage("Password must contain at least one lowercase letter");
    }

    @Test
    @DisplayName("Should throw exception when password lacks a numeric digit")
    void shouldRejectMissingDigit() {
        assertThatThrownBy(() -> new Password("P@sswordX"))
                .isInstanceOf(IllegalPasswordException.class)
                .hasMessage("Password must contain at least one digit");
    }

    @Test
    @DisplayName("Should throw exception when password lacks a recognized special character")
    void shouldRejectMissingSpecialCharacter() {
        assertThatThrownBy(() -> new Password("P1ssword"))
                .isInstanceOf(IllegalPasswordException.class)
                .hasMessage("Password must contain at least one special character");
    }

    @Test
    @DisplayName("Should ensure structural equality (equals and hashCode) for matching values")
    void shouldRespectEqualsAndHashCodeContract() {
        String rawPassword = "SecurePass123!";
        Password password1 = new Password(rawPassword);
        Password password2 = new Password(rawPassword);
        Password differentPassword = new Password("OtherPass456#");

        // Equals checks
        assertThat(password1).isEqualTo(password2);
        assertThat(password1).isNotEqualTo(differentPassword);
        assertThat(password1).isNotEqualTo(null);

        // HashCode checks
        assertThat(password1.hashCode()).isEqualTo(password2.hashCode());
        assertThat(password1.hashCode()).isNotEqualTo(differentPassword.hashCode());
    }
}