package com.cleancode.ecommerce.shared.kernel;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalPasswordException;

public class Password {

	private final String value;

    public Password(String password) {
        validateAcceptablePasswordFormat(password);
        this.value = password; 
    }

    public String getValue() {
        return value;
    }

    private void validateAcceptablePasswordFormat(String password) {
        final int MINIMUM_PASSWORD_SIZE = 8;
        if (password == null || password.isBlank() || password.length() < MINIMUM_PASSWORD_SIZE) {
            throw new IllegalPasswordException("Password must contain at least 8 characters");
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (isSpecialChar(c)) hasSpecial = true;
        }

        if (!hasUpper) throw new IllegalPasswordException("Password must contain at least one uppercase letter");
        if (!hasLower) throw new IllegalPasswordException("Password must contain at least one lowercase letter");
        if (!hasDigit) throw new IllegalPasswordException("Password must contain at least one digit");
        if (!hasSpecial) throw new IllegalPasswordException("Password must contain at least one special character");
    }

    private boolean isSpecialChar(char c) {
        final String specialChars = "!@#$%^&*()_+-=[]{};:'\"\\|,.<>/?";
        return specialChars.indexOf(c) >= 0;
    }

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Password other = (Password) obj;
		return Objects.equals(value, other.value);
	}
}
