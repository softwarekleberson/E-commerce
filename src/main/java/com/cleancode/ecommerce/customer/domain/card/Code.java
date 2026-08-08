package com.cleancode.ecommerce.customer.domain.card;

import java.util.Objects;

import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;

public class Code {

	public static final int CODE_NUMBER = 3;
	private final String code;

	public Code(String code) {
		if (code.length() != CODE_NUMBER) {
			throw new IllegalCardException("Code is required in card and needs 3 numbers");
		}
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Code other = (Code) obj;
		return Objects.equals(code, other.code);
	}
}
