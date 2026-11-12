package com.cleancode.ecommerce.promotional.domain;

import java.util.Objects;

import com.cleancode.ecommerce.adm.domain.exception.IllegalAdmException;

public class CodeVoucher {

	private final String code;

	public CodeVoucher(String code) {
		if (code == null || code.isBlank()) {
			throw new IllegalAdmException("Code cannot be null or empty");
		}
		this.code = code.toUpperCase();
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
		CodeVoucher other = (CodeVoucher) obj;
		return Objects.equals(code, other.code);
	}
}
