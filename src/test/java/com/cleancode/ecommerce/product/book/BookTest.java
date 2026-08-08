package com.cleancode.ecommerce.product.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Dimension;
import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.books.Author;
import com.cleancode.ecommerce.product.domain.books.Book;
import com.cleancode.ecommerce.product.domain.books.CategoryBook;
import com.cleancode.ecommerce.product.domain.books.Edition;
import com.cleancode.ecommerce.product.domain.books.Isbn;
import com.cleancode.ecommerce.product.domain.books.Page;
import com.cleancode.ecommerce.product.domain.books.PublisherDate;
import com.cleancode.ecommerce.product.domain.books.Synopsis;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public class BookTest {

	private Book createSampleBook() {
		return new Book(new Name("Effective Java"), new Description("A comprehensive guide to best practices in Java"),
				new Price(BigDecimal.valueOf(199.90), TypeCoin.REAL), ProductCategory.BOOKS,
				new Brand("Addison-Wesley"), List.of(new Media("https://site.com/cover.jpg", "Cover")),
				new Pricing(BigDecimal.valueOf(0.10)), new Synopsis("Covers design patterns and idioms."), new Page(416),
				new Author("Joshua Bloch"), new Edition("3rd Edition"), new Isbn("9780134685991"),
				CategoryBook.BIOGRAPHY, new Dimension(23.5, 18.0, 3.0, 1.2),
				new PublisherDate(LocalDate.of(2018, 1, 6)));
	}

	@Test
	@DisplayName("Should create a valid Book and return correct attributes")
	void shouldCreateValidBook() {
		Book book = createSampleBook();

		assertEquals("Effective Java", book.getName().getName());
		assertEquals("Joshua Bloch", book.getAuthor().getAuthor());
		assertEquals(416, book.getPage().getPage());
		assertEquals("9780134685991", book.getIsbn().getIsbn());
		assertEquals(CategoryBook.BIOGRAPHY, book.getCategoryBook());
		assertEquals("3rd Edition", book.getEdition().getEdition());
		assertEquals(LocalDate.of(2018, 1, 6), book.getPublisherDate().getPublisherDate());
		assertEquals(23.5, book.getDimension().getHeight());
	}

	@Test
	@DisplayName("Should not be equal when any field is different")
	void shouldNotBeEqualWhenFieldsDiffer() {
		Book book1 = createSampleBook();

		Book book2 = new Book(new Name("Clean Code"), new Description("Another great book"),
				new Price(BigDecimal.valueOf(149.90), TypeCoin.REAL), ProductCategory.BOOKS, new Brand("Prentice Hall"),
				List.of(new Media("https://site.com/cover2.jpg", "Cover")), new Pricing(BigDecimal.valueOf(0.10)),
				new Synopsis("Learn how to write clean code."), new Page(464), new Author("Robert C. Martin"),
				new Edition("1st Edition"), new Isbn("9780132350884"), CategoryBook.BIOGRAPHY,
				new Dimension(21.0, 14.8, 2.5, 0.9), new PublisherDate(LocalDate.of(2008, 8, 11)));

		assertNotEquals(book1, book2);
	}

	@Test
	@DisplayName("Should return true when comparing to itself")
	void shouldBeEqualToItself() {
		Book book = createSampleBook();
		assertEquals(book, book);
	}

	@Test
	@DisplayName("Should return false when comparing with null")
	void shouldNotBeEqualToNull() {
		Book book = createSampleBook();
		assertNotEquals(null, book);
	}

	@Test
	@DisplayName("Should return false when comparing with different class")
	void shouldNotBeEqualToDifferentClass() {
		Book book = createSampleBook();
		Object obj = new Object();
		assertNotEquals(book, obj);
	}
}