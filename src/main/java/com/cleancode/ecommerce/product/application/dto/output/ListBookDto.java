package com.cleancode.ecommerce.product.application.dto.output;

import java.io.Serializable;
import java.time.LocalDate;

import com.cleancode.ecommerce.product.domain.books.CategoryBook;

public class ListBookDto extends ListProductDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String synopsis;
	private int page;
	private String author;
	private String edition;
	private String isbn;
	private CategoryBook categoryBook;
	private double height;
	private double width;
	private double length;
	private double weight;
	private LocalDate publisherDate;

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public CategoryBook getCategoryBook() {
		return categoryBook;
	}

	public void setCategoryBook(CategoryBook categoryBook) {
		this.categoryBook = categoryBook;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public LocalDate getPublisherDate() {
		return publisherDate;
	}

	public void setPublisherDate(LocalDate publisherDate) {
		this.publisherDate = publisherDate;
	}
}