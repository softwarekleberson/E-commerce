package com.cleancode.ecommerce.shared.kernel;

import java.util.Objects;

import com.cleancode.ecommerce.customer.domain.customer.exception.IllegalContactException;

public class Email {

	private final String email;
	
	public Email(String email) {
		if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalContactException("Email is incorrect");
		}
		
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public String toString() {
		return "Email [email=" + email + "]";
	}	
}