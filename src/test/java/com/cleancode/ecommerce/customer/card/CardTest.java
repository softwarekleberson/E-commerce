package com.cleancode.ecommerce.customer.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.card.Card;
import com.cleancode.ecommerce.customer.domain.card.Code;
import com.cleancode.ecommerce.customer.domain.card.ExpirationDate;
import com.cleancode.ecommerce.customer.domain.card.Flag;
import com.cleancode.ecommerce.customer.domain.card.NumberCard;
import com.cleancode.ecommerce.customer.domain.card.PrintedName;

public class CardTest {

	private PrintedName printedName;
    private Code code;
    private NumberCard numberCard;
    private ExpirationDate expirationDate;
    private Flag flag;

    @BeforeEach
    void setup() {
        printedName = new PrintedName("Kleberson Santos");
        code = new Code("123");
        numberCard = new NumberCard("5309272522600420");
        expirationDate = new ExpirationDate(LocalDate.now().plusYears(2));
        flag = Flag.VISA;
    }

    @Test
    void shouldCreateCardWithValidData() {
        Card card = new Card(true, printedName, code, numberCard, expirationDate, flag);

        assertNotNull(card.getCardId());
        assertTrue(card.isMain());
        assertEquals(printedName, card.getPrintedName());
        assertEquals(code, card.getCode());
        assertEquals(numberCard, card.getNumberCard());
        assertEquals(expirationDate, card.getExpirationDate());
        assertEquals(flag, card.getFlag());
    }

    @Test
    void cardsWithSameValuesShouldBeEqual() {
        Card card1 = new Card(true, printedName, code, numberCard, expirationDate, flag);
        Card card2 = new Card(true, printedName, code, numberCard, expirationDate, flag);

        assertNotEquals(card1, card2);

        assertEquals(card1, card1);
        assertEquals(card2, card2);
    }

    @Test
    void shouldRespectHashCodeContract() {
        Card card1 = new Card(true, printedName, code, numberCard, expirationDate, flag);
        Card card2 = new Card(true, printedName, code, numberCard, expirationDate, flag);

        assertNotEquals(card1.hashCode(), card2.hashCode()); 
    }

    @Test
    void shouldNotBeEqualToNullOrDifferentClass() {
        Card card = new Card(false, printedName, code, numberCard, expirationDate, flag);

        assertNotEquals(card, null);
        assertNotEquals(card, "string qualquer");
    }
}
