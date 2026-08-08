package com.cleancode.ecommerce.customer.domain.card;

import java.util.Objects;
import java.util.UUID;

public class CardId {

private final UUID value;
	
	// Named factory method or constructor for new IDs
	public CardId() {
		this.value = UUID.randomUUID();
	}
	
	// Constructor used when restoring from String (e.g., Database/Mapper)
	public CardId(String cardId) {
		Objects.requireNonNull(cardId, "Card ID cannot be null");
		this.value = UUID.fromString(cardId); // Automatically validates UUID format
	}

	public CardId(UUID value) {
		this.value = Objects.requireNonNull(value, "Card ID value cannot be null");
	}
	
	// Expressive getter that exposes the raw primitive/wrapper value clearly
	public UUID getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		CardId other = (CardId) obj;
		return Objects.equals(value, other.value);
	}
}
