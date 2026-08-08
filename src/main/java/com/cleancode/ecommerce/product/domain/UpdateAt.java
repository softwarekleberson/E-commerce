package com.cleancode.ecommerce.product.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class UpdateAt {

	private final LocalDateTime updateAt;
	
	public UpdateAt() {
		this.updateAt = LocalDateTime.now();
	}
	
	public UpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public static UpdateAt update() {
		return new UpdateAt();
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(updateAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateAt other = (UpdateAt) obj;
		return Objects.equals(updateAt, other.updateAt);
	}	
}