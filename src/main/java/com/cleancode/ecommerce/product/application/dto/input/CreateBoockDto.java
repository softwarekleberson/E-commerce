package com.cleancode.ecommerce.product.application.dto.input;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Dimension;
import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.Product;
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

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class CreateBoockDto extends CreateProductDto {

	@NotBlank(message = "Synopsi is required")
	private String synopsis;
	
	@Min(1)
	private int page;
	
	@NotBlank(message = "Autor is required")
	private String author;
	
	@NotBlank(message = "Edition is required")
	private String edition;
	
	@NotBlank(message = "Isbn is required")
	private String isbn;
	
	@NotNull(message = "Category book is required")
	private CategoryBook categoryBoock;
	
	@Min(0)
	private double height;
	
	@Min(0)
	private double width;
	
	@Min(0)
	private double length;
	
	@Min(0)
	private double weight;
	
	@NotNull
	@PastOrPresent
	private LocalDate publisherDate;

	public CreateBoockDto(String name, String description, TypeCoin typeCoin,
			ProductCategory category, String brand, List<MidiaInputDto> midias, BigDecimal pricing, String synopsis,
			int page, String author, String edition, String isbn, CategoryBook categoryBoock, double height,
			double width, double length, double weight, LocalDate publisherDate) {

		super(name, description, typeCoin, ProductCategory.BOOKS, brand, midias, pricing);
		this.synopsis = synopsis;
		this.page = page;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
		this.categoryBoock = categoryBoock;
		this.height = height;
		this.width = width;
		this.length = length;
		this.weight = weight;
		this.publisherDate = publisherDate;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public int getPage() {
		return page;
	}

	public String getAuthor() {
		return author;
	}

	public String getEdition() {
		return edition;
	}

	public String getIsbn() {
		return isbn;
	}

	public CategoryBook getCategoryBoock() {
		return categoryBoock;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getLength() {
		return length;
	}

	public double getWeight() {
		return weight;
	}

	public LocalDate getPublisherDate() {
		return publisherDate;
	}

	@Override
	public Product toProduct() {
		List<Media> midiasDomain = extractedMidia();

		return new Book(new Name(getName()), new Description(getDescription()), new Price(getPrice(), getTypeCoin()),
				getCategory(), new Brand(getBrand()), midiasDomain, new Pricing(getPricing()),
				new Synopsis(getSynopsis()), new Page(getPage()), new Author(getAuthor()), new Edition(getEdition()),
				new Isbn(getIsbn()), categoryBoock, new Dimension(height, width, length, weight),
				new PublisherDate(getPublisherDate()));
	}
}