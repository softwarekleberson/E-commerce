package com.cleancode.ecommerce.product.book;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.books.Page;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

class PageTest {

    @Test
    @DisplayName("Should create a valid page with positive number")
    void testCreateValidPage() {
        Page page = new Page(10);
        assertEquals(10, page.getPage());
    }

    @Test
    @DisplayName("Should throw exception when creating page with number less than 1")
    void testInvalidPageThrowsException() {
        IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> {
            new Page(0);
        });
        assertEquals("Number page not be less than 0", exception.getMessage());
    }

    @Test
    @DisplayName("Should consider pages equal if number is the same")
    void testEqualsAndHashCode() {
        Page p1 = new Page(100);
        Page p2 = new Page(100);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("Should consider pages different if numbers are different")
    void testNotEquals() {
        Page p1 = new Page(10);
        Page p2 = new Page(20);

        assertNotEquals(p1, p2);
    }

    @Test
    @DisplayName("Should return false when comparing with object of another class")
    void testEqualsWithDifferentClass() {
        Page p = new Page(5);
        Object obj = new Object();

        assertNotEquals(p, obj);
    }

    @Test
    @DisplayName("Should return true when comparing page with itself")
    void testEqualsWithSameInstance() {
        Page p = new Page(7);
        assertEquals(p, p);
    }

    @Test
    @DisplayName("Should return false when comparing page with null")
    void testEqualsWithNull() {
        Page p = new Page(3);
        assertNotEquals(null, p);
    }
}
