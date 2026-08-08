package com.cleancode.ecommerce.product.domain.books;

import java.util.List;
import java.util.Objects;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Dimension;
import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;

public class Book extends Product {

	private final Synopsis synopsis;
	private final Page page;
	private final Author author;
	private final Edition edition;
	private final Isbn isbn;
	private final CategoryBook categoryBook;
	private final Dimension dimension;
	private final PublisherDate publisherDate;

	public Book(Name name, Description description, Price price, ProductCategory category, Brand brand,
			List<Media> midia, Pricing pricing,Synopsis synopsis, Page page, Author author, Edition edition, Isbn isbn,
			CategoryBook categoryBook, Dimension dimension, PublisherDate publisherDate) {

		super(name, description, price, category, brand, midia, pricing);
		this.synopsis = synopsis;
		this.page = page;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
		this.categoryBook = categoryBook;
		this.dimension = dimension;
		this.publisherDate = publisherDate;
	}
	
	public Book(ProductId idProduct, boolean active, Name name, Description description, Price price,
			ProductCategory category, Brand brand, List<Media> midia, Pricing pricing, Synopsis synopsis, Page page, Author author, Edition edition, Isbn isbn, CategoryBook categoryBook,
			Dimension dimension, PublisherDate publisherDate) {
		
		super(idProduct, active, name, description, price, category, brand, midia, pricing);
		
		this.synopsis = synopsis;
		this.page = page;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
		this.categoryBook = categoryBook;
		this.dimension = dimension;
		this.publisherDate = publisherDate;
	}

	public Synopsis getSynopsis() {
		return synopsis;
	}

	public Page getPage() {
		return page;
	}

	public Author getAuthor() {
		return author;
	}

	public Edition getEdition() {
		return edition;
	}

	public Isbn getIsbn() {
		return isbn;
	}

	public CategoryBook getCategoryBook() {
		return categoryBook;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public PublisherDate getPublisherDate() {
		return publisherDate;
	}

	@Override
	public String toString() {
		return "Book [synopsis=" + synopsis + ", page=" + page + ", author=" + author + ", edition=" + edition
				+ ", isbn=" + isbn + ", categoryBook=" + categoryBook + ", dimension=" + dimension + ", publisherDate="
				+ publisherDate + "]";
	}
}