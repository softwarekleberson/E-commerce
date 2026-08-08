package com.cleancode.ecommerce.customer.infra.mapper;

import com.cleancode.ecommerce.customer.domain.card.Card;
import com.cleancode.ecommerce.customer.domain.card.CardId; 
import com.cleancode.ecommerce.customer.domain.card.Code;
import com.cleancode.ecommerce.customer.domain.card.ExpirationDate;
import com.cleancode.ecommerce.customer.domain.card.Flag;
import com.cleancode.ecommerce.customer.domain.card.NumberCard;
import com.cleancode.ecommerce.customer.domain.card.PrintedName;
import com.cleancode.ecommerce.customer.infra.persistence.card.CardEntity;
import com.cleancode.ecommerce.customer.infra.persistence.card.FlagEntity;
import com.cleancode.ecommerce.customer.infra.persistence.customer.CustomerEntity;
import java.util.UUID;

public final class CardMapper {

	private CardMapper() {
	}

	public static CardEntity toEntity(Card card, CustomerEntity customerEntity) {
		if (card == null) {
			return null;
		}

		CardEntity entity = new CardEntity();
		// Reduced train-wreck navigation. Consider adding card.getCardIdAsString()
		entity.setCard_id(card.getCardId().toString());
		entity.setMain(card.isMain());
		entity.setPrinted_name(card.getPrintedName().getName());
		entity.setCode(card.getCode().getCode());
		entity.setNumber_card(card.getNumberCard().getNumberCard());
		entity.setExpiration_date(card.getExpirationDate().getExpirationDate());
		
		if (card.getFlag() != null) {
			entity.setFlag(FlagEntity.valueOf(card.getFlag().name()));
		}
		
		entity.setCustomer(customerEntity);
		return entity;
	}

	public static void updateEntity(Card card, CardEntity entity) {
		if (card == null || entity == null) {
			return;
		}
	
		entity.setCard_id(card.getCardId().toString());
		entity.setMain(card.isMain());
		entity.setPrinted_name(card.getPrintedName().getName());
		entity.setCode(card.getCode().getCode());
		entity.setNumber_card(card.getNumberCard().getNumberCard());
		entity.setExpiration_date(card.getExpirationDate().getExpirationDate());
		
		if (card.getFlag() != null) {
			entity.setFlag(FlagEntity.valueOf(card.getFlag().name()));
		}
	}
	
	public static Card toDomain(CardEntity entity) {
		if (entity == null) {
			return null;
		}

		// Critical Fix: Reconstruct the domain object with its original identity
		CardId cardId = new CardId(UUID.fromString(entity.getCard_id()));

		return new Card(
				cardId, // Make sure your Card constructor accepts the reconstructed CardId
				entity.isMain(),
				new PrintedName(entity.getPrinted_name()),
				new Code(entity.getCode()),
				new NumberCard(entity.getNumber_card()),
				new ExpirationDate(entity.getExpiration_date()),
				entity.getFlag() != null ? Flag.valueOf(entity.getFlag().name()) : null
		);
	}
}