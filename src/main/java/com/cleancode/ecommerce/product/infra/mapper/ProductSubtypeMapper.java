package com.cleancode.ecommerce.product.infra.mapper;

import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductEntity;

/** Maps one concrete product subtype to and from its persistence entity. */
public interface ProductSubtypeMapper<P extends Product, E extends ProductEntity> {

	Class<P> domainType();

	Class<E> entityType();

	P toDomain(E entity);

	E toEntity(P product);

	default boolean supportsDomain(Product product) {
		return domainType().isInstance(product);
	}

	default boolean supportsEntity(ProductEntity entity) {
		return entityType().isInstance(entity);
	}

	default Product mapToDomain(ProductEntity entity) {
		return toDomain(entityType().cast(entity));
	}

	default ProductEntity mapToEntity(Product product) {
		return toEntity(domainType().cast(product));
	}
}
