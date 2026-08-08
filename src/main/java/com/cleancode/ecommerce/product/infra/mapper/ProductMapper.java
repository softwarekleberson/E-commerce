package com.cleancode.ecommerce.product.infra.mapper;

import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.bag.Bag;
import com.cleancode.ecommerce.product.domain.books.Book;
import com.cleancode.ecommerce.product.infra.mapper.bag.BagMapper;
import com.cleancode.ecommerce.product.infra.mapper.book.BookMapper;
import com.cleancode.ecommerce.product.infra.persistence.bag.BagEntity;
import com.cleancode.ecommerce.product.infra.persistence.book.BookEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductEntity;

public class ProductMapper {

	private ProductMapper() {
	}

	public static Product toDomain(ProductEntity entity) {
		if (entity instanceof BagEntity bagEntity) {
			return BagMapper.toDomain((BagEntity) entity);
		}

		if (entity instanceof BookEntity bookEntity) {
			return BookMapper.toDomain((BookEntity) entity);
		}
		
		throw new UnsupportedOperationException("Type product not suport: " + entity.getClass());
	}

	public static ProductEntity toEntity(Product domain) {
		if (domain instanceof Bag) {
			return BagMapper.toEntity((Bag) domain);
		}
		
		if (domain instanceof Book) {
			return BookMapper.toEntity((Book) domain);
		}

		throw new UnsupportedOperationException("Type product not suport: " + domain.getClass());
	}
}