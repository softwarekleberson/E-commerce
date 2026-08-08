package com.cleancode.ecommerce.customer.application.dtos.card;

import com.cleancode.ecommerce.customer.domain.card.Card;

public record ListCardDto(String cardId, boolean main, String printedName, String numberCard) {

	public ListCardDto(Card card) {
		this(card.getCardId().toString(), card.isMain(), card.getPrintedName().getName(), card.getNumberCard().getNumberCard());
	}
}
