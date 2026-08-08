package com.cleancode.ecommerce.product.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.books.Author;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class AuthorTest {

	@Test
    @DisplayName("Should create a valid author")
    void shouldCreateValidAuthor() {
        Author author = new Author("J. K. Rowling");
        assertEquals("J. K. Rowling", author.getAuthor());
    }

    @Test
    @DisplayName("Should throw exception when author is null")
    void shouldThrowExceptionWhenAuthorIsNull() {
        IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> {
            new Author(null);
        });
        assertEquals("Author not ne null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when author is empty or blank")
    void shouldThrowExceptionWhenAuthorIsEmpty() {
        IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> {
            new Author("   ");
        });
        assertEquals("Author not ne null", exception.getMessage());
    }

    @Test
    @DisplayName("Should consider authors equal when the name is the same")
    void shouldBeEqualWhenAuthorNameIsSame() {
        Author a1 = new Author("George Orwell");
        Author a2 = new Author("George Orwell");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    @DisplayName("Should not be equal when the author names are different")
    void shouldNotBeEqualWhenAuthorNameIsDifferent() {
        Author a1 = new Author("George Orwell");
        Author a2 = new Author("Aldous Huxley");

        assertNotEquals(a1, a2);
    }

    @Test
    @DisplayName("Should return false when comparing with an object of a different class")
    void shouldNotBeEqualToDifferentClass() {
        Author a1 = new Author("Isaac Asimov");
        Object obj = new Object();

        assertNotEquals(a1, obj);
    }

    @Test
    @DisplayName("Should return true when comparing with itself")
    void shouldBeEqualToItself() {
        Author a1 = new Author("Ray Bradbury");

        assertEquals(a1, a1);
    }

    @Test
    @DisplayName("Should return false when comparing with null")
    void shouldNotBeEqualToNull() {
        Author a1 = new Author("Philip K. Dick");

        assertNotEquals(null, a1);
    }
}
