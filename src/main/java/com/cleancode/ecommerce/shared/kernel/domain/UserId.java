package com.cleancode.ecommerce.user.domain;

import java.util.Objects;
import java.util.UUID;

public class UserId {

	private final String userId;
	
	public UserId(String userId) {
		this.userId = Objects.requireNonNull(userId);
	}
	
	public UserId() {
		this.userId = UUID.randomUUID().toString();
	}
	
	public String getUserId() {
		return userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserId other = (UserId) obj;
		return Objects.equals(userId, other.userId);
	}
}
