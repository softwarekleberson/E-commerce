package com.cleancode.ecommerce.product.infra.mapper.book;

import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Dimension;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.product.domain.books.Author;
import com.cleancode.ecommerce.product.domain.books.Book;
import com.cleancode.ecommerce.product.domain.books.CategoryBook;
import com.cleancode.ecommerce.product.domain.books.Edition;
import com.cleancode.ecommerce.product.domain.books.Isbn;
import com.cleancode.ecommerce.product.domain.books.Page;
import com.cleancode.ecommerce.product.domain.books.PublisherDate;
import com.cleancode.ecommerce.product.domain.books.Synopsis;
import com.cleancode.ecommerce.product.infra.mapper.MidiaInputMapper;
import com.cleancode.ecommerce.product.infra.persistence.book.BookEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.MidiaEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductCategoryEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductStatusCategoryEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.TypeCoinEntity;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public class BookMapper {

	public static Book toDomain(BookEntity entity) {
		return new Book(new ProductId(entity.getProduct_id()), entity.isActive(), new Name(entity.getName()),
				new Description(entity.getDescription()),
				new Price(entity.getPrice(), TypeCoin.valueOf(entity.getType_coin().name())),
				ProductCategory.valueOf(entity.getCategory().name()), new Brand(entity.getBrand()),
				MidiaInputMapper.toMidiaList(entity.getMidias()), new Pricing(entity.getPricing()),
				new Synopsis(entity.getSynopsis()), new Page(entity.getPage()), new Author(entity.getAuthor()),
				new Edition(entity.getEdition()), new Isbn(entity.getIsbn()),
				CategoryBook.valueOf(entity.getCategoryBook().name()),
				new Dimension(entity.getHeight(), entity.getWidth(), entity.getLength(), entity.getWeight()),
				new PublisherDate(entity.getPublisherDate()));
	}

	public static BookEntity toEntity(Book domain) {
		BookEntity entity = new BookEntity();

		entity.setProduct_id(domain.getProductId().getProductId());
		entity.setActive(domain.isActive());
		entity.setName(domain.getName().getName());
		entity.setDescription(domain.getDescription().getDescription());
		entity.setPrice(domain.getPrice().getPrice());
		entity.setType_coin(TypeCoinEntity.valueOf(domain.getPrice().getCoin().name()));
		entity.setCategory(ProductCategoryEntity.valueOf(domain.getProductCategory().name()));
		entity.setBrand(domain.getBrand().getBrand());

		List<MidiaEntity> imageEntities = domain.getMidia().stream().map(midia -> {
			MidiaEntity imageEntity = new MidiaEntity();
			imageEntity.setMidia_id(midia.getId());
			imageEntity.setUrl(midia.getUrl());
			imageEntity.setDescription(midia.getDescription());
			imageEntity.setProduct(entity);
			return imageEntity;
		}).collect(Collectors.toList());
		entity.setMidias(imageEntities);
		if (domain.getProductStatusPolicy() != null) {
			entity.setJustification(domain.getProductStatusPolicy().getJustification());

			if (domain.getProductStatusPolicy().getCategory() != null) {
				entity.setProduct_status(
						ProductStatusCategoryEntity.valueOf(domain.getProductStatusPolicy().getCategory().name()));
			}
		}

		entity.setPricing(domain.getPricing().getPricing());
		entity.setSynopsis(domain.getSynopsis().getSynopsis());
		entity.setPage(domain.getPage().getPage());
		entity.setEdition(domain.getEdition().getEdition());
		entity.setAuthor(domain.getAuthor().getAuthor());
		entity.setIsbn(domain.getIsbn().getIsbn());
		entity.setCategoryBook(CategoryBook.valueOf(domain.getCategoryBook().name()));
		entity.setHeight(domain.getDimension().getHeight());
		entity.setWidth(domain.getDimension().getWidth());
		entity.setLength(domain.getDimension().getLength());
		entity.setWeight(domain.getDimension().getWeight());
		entity.setPublisherDate(domain.getPublisherDate().getPublisherDate());

		return entity;
	}
}