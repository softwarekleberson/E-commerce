package com.cleancode.ecommerce.product.application.dto.output;

import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.bag.Bag;
import com.cleancode.ecommerce.product.domain.books.Book;
import com.cleancode.ecommerce.product.infra.mapper.MidiaOutputMapper;

public class ProductDtoFactory {

	public static ListProductDto listAllProduct(Product product) {

		if (product instanceof Bag bag) {
			ListBagDto dto = new ListBagDto();
			dto.setId(bag.getProductId().getProductId());
			dto.setName(bag.getName().getName());
			dto.setDescription(bag.getDescription().getDescription());
			dto.setPrice(bag.getPrice().getPrice());
			dto.setTypeCoin(bag.getPrice().getCoin());
			dto.setCategory(bag.getProductCategory());
			dto.setBrand(bag.getBrand().getBrand());
			dto.setMidias(MidiaOutputMapper.toOutputDtoList(bag.getMidia()));
			dto.setVolume(bag.getVolume().getVolume());
			dto.setColor(bag.getColor().getColor());
			return dto;
		}

		if (product instanceof Book book) {
			ListBookDto dto = new ListBookDto();
			dto.setId(book.getProductId().getProductId());
			dto.setName(book.getName().getName());
			dto.setDescription(book.getDescription().getDescription());
			dto.setPrice(book.getPrice().getPrice());
			dto.setTypeCoin(book.getPrice().getCoin());
			dto.setCategory(book.getProductCategory());
			dto.setBrand(book.getBrand().getBrand());
			dto.setMidias(MidiaOutputMapper.toOutputDtoList(book.getMidia()));
			dto.setSynopsis(book.getSynopsis().getSynopsis());
			dto.setPage(book.getPage().getPage());
			dto.setAuthor(book.getAuthor().getAuthor());
			dto.setEdition(book.getEdition().getEdition());
			dto.setIsbn(book.getIsbn().getIsbn());
			dto.setCategoryBook(book.getCategoryBook());
			dto.setHeight(book.getDimension().getHeight());
			dto.setWidth(book.getDimension().getWidth());
			dto.setLength(book.getDimension().getLength());
			dto.setWeight(book.getDimension().getWeight());
			dto.setPublisherDate(book.getPublisherDate().getPublisherDate());			
			return dto;
		}

		throw new IllegalArgumentException("Unknown product type: " + product.getClass());
	}
}