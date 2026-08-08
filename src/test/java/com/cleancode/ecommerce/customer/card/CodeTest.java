package com.cleancode.ecommerce.customer.card;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.card.Code;
import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;

class CodeTest {

    @Test
    void shouldCreateCodeWhenValid() {
        String validCode = "123";
        Code code = new Code(validCode);

        assertNotNull(code);
        assertEquals(validCode, code.getCode());
    }

    @Test
    void shouldThrowExceptionWhenCodeIsShort() {
        Exception exception = assertThrows(IllegalCardException.class, () -> {
            new Code("12");
        });

        assertEquals("Code is required in card and needs 3 numbers", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCodeIsLong() {
        Exception exception = assertThrows(IllegalCardException.class, () -> {
            new Code("1234");
        });

        assertEquals("Code is required in card and needs 3 numbers", exception.getMessage());
    }

    @Test
    void shouldBeEqualWhenCodesAreSame() {
        Code code1 = new Code("123");
        Code code2 = new Code("123");

        assertEquals(code1, code2);
        assertEquals(code1.hashCode(), code2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenCodesAreDifferent() {
        Code code1 = new Code("123");
        Code code2 = new Code("456");

        assertNotEquals(code1, code2);
    }

    @Test
    void shouldNotBeEqualToNullOrDifferentClass() {
        Code code = new Code("123");

        assertNotEquals(code, null);
        assertNotEquals(code, "123");
    }
}
