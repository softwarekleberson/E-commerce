package com.cleancode.ecommerce.customer.domain.contact;

import java.util.Objects;

import com.cleancode.ecommerce.shared.kernel.Email;

public class Contact {

	private final Phone phone;
	private final Email email;
	
	public Contact(Phone phone, Email email) {
		this.phone = phone;
		this.email = email;
	}
	
	public Phone getFullPhone() {
		return phone;
	}
	
	public String getPhone() {
		return phone.getPhone();
	}
	
	public String getDDD() {
		return phone.getDdd();
	}
	
	public TypePhone getTypePhone() {
		return phone.getTypePhone();
	}
	
	public Email getEmail() {
		return email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		return Objects.equals(email, other.email) && Objects.equals(phone, other.phone);
	}
}
