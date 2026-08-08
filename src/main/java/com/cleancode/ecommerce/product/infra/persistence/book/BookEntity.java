package com.cleancode.ecommerce.product.infra.persistence.book;

import java.time.LocalDate;

import com.cleancode.ecommerce.product.domain.books.CategoryBook;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_book")
@DiscriminatorValue("book")
public class BookEntity extends ProductEntity{

	private String synopsis;
	private int page;
	private String author;
	private String edition;
	private String isbn;
	
	@Column(name = "category_book")
	@Enumerated(EnumType.STRING)
	private CategoryBook categoryBook;
	private double height;
	private double width;
	private double length;
	private double weight;
	
	@Column(name = "publisher_date")
	private LocalDate publisherDate;
}