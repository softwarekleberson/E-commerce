package com.cleancode.ecommerce.customer.domain.card;

import java.util.Objects;

import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;

public class NumberCard {

	private final String numberCard;

	public NumberCard(String numberCard) {
		if (numberCard == null || !numberCard.matches("\\d{16}")) {
			throw new IllegalCardException("Card number must have exactly 16 digits");
		}

		int sum = 0;
		boolean toggle = false;

		for (int i = numberCard.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(numberCard.substring(i, i + 1));
			if (toggle) {
				n *= 2;
				if (n > 9) {
					n -= 9;
				}
			}
			sum += n;
			toggle = !toggle;
		}

		if (sum % 10 != 0) {
			throw new IllegalCardException("Invalid card number (Luhn check failed)");
		}

		this.numberCard = numberCard;
	}

	public String getNumberCard() {
		return numberCard;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numberCard);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumberCard other = (NumberCard) obj;
		return Objects.equals(numberCard, other.numberCard);
	}
}