package com.cleancode.ecommerce.product.infra.gateway;

import java.util.Optional;

import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.product.infra.mapper.ProductMapper;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductRepositoryJpa implements ProductRepository {

	private final ProductJpa jpa;

	public ProductRepositoryJpa(ProductJpa jpa) {
		this.jpa = jpa;
	}

	@Transactional
	@Override
	public Product save(Product product) {
		ProductEntity entity = ProductMapper.toEntity(product);
		jpa.save(entity);
		Product domain = ProductMapper.toDomain(entity);
		return domain;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Product> ListAllProductActive(Pageable pageable) {
	    Page<ProductEntity> entities = jpa.findByActiveTrue(pageable);
	    return entities.map(ProductMapper::toDomain);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Product> listAllProductNotActive(Pageable pageable) {
	    Page<ProductEntity> entities = jpa.findByActiveFalse(pageable);
	    return entities.map(ProductMapper::toDomain);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Product> findById(String productId) {
		return jpa.findById(productId).map(ProductMapper::toDomain);
	}
}