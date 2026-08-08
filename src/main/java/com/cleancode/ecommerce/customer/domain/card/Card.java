package com.cleancode.ecommerce.customer.domain.card;

import java.util.Objects;

public class Card {

	private CardId cardId;
	private boolean main;
	private PrintedName printedName;
	private Code code;
	private NumberCard numberCard;
	private ExpirationDate expirationDate;
	private Flag flag;
	private Credit credit;

	public Card(boolean main, PrintedName printedName, Code code, NumberCard numberCard, ExpirationDate expirationDate,
			Flag flag) {

		this.cardId = new CardId();
		this.main = main;
		this.printedName = printedName;
		this.code = code;
		this.numberCard = numberCard;
		this.expirationDate = expirationDate;
		this.flag = flag;
		this.credit = new Credit();
	}

	public Card(CardId cardId, boolean main, PrintedName printedName, Code code, NumberCard numberCard, ExpirationDate expirationDate, Flag flag) {
        this.cardId = cardId; // Reuses the ID coming from the database
        this.main = main;
        this.printedName = printedName;
        this.code = code;
        this.numberCard = numberCard;
        this.expirationDate = expirationDate;
        this.flag = flag;
    }

	public void disableMain() {
		this.main = false;
	}

	public CardId getCardId() {
		return cardId;
	}

	public boolean isMain() {
		return main;
	}

	public PrintedName getPrintedName() {
		return printedName;
	}

	public Code getCode() {
		return code;
	}

	public NumberCard getNumberCard() {
		return numberCard;
	}

	public ExpirationDate getExpirationDate() {
		return expirationDate;
	}

	public Flag getFlag() {
		return flag;
	}

	public Credit getCredit() {
		return credit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cardId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return Objects.equals(cardId, other.cardId);
	}
}