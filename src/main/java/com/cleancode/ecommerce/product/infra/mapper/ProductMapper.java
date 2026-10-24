package com.cleancode.ecommerce.product.infra.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductEntity;

@Component
public class ProductMapper {

	private final List<ProductSubtypeMapper<?, ?>> subtypeMappers;

	public ProductMapper(List<ProductSubtypeMapper<?, ?>> subtypeMappers) {
		this.subtypeMappers = List.copyOf(subtypeMappers);
	}

	public Product toDomain(ProductEntity entity) {
		return subtypeMappers.stream()
				.filter(mapper -> mapper.supportsEntity(entity))
				.findFirst()
				.map(mapper -> mapper.mapToDomain(entity))
				.orElseThrow(() -> new UnsupportedOperationException(
						"Type product not suport: " + entity.getClass()));
	}

	public ProductEntity toEntity(Product domain) {
		return subtypeMappers.stream()
				.filter(mapper -> mapper.supportsDomain(domain))
				.findFirst()
				.map(mapper -> mapper.mapToEntity(domain))
				.orElseThrow(() -> new UnsupportedOperationException(
						"Type product not suport: " + domain.getClass()));
	}
}
