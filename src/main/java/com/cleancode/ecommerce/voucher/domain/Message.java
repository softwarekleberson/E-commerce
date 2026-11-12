package com.cleancode.ecommerce.promotional.domain;

import java.util.Objects;

public class Message {

	private String message;

	public Message(String message) {
		this.message = Objects.requireNonNull(message);
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		return Objects.equals(message, other.message);
	}
}
