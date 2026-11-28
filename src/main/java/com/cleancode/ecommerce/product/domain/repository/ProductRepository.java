package com.cleancode.ecommerce.product.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.ProductSearchCriteria;

public interface ProductRepository {

	Product save(Product product);
	Page<Product> ListAllProductActive(Pageable pageable);
	Page<Product> listAllProductNotActive(Pageable pageable);
	Optional<Product> findById(String idProduct);
	Page<Product> search(ProductSearchCriteria criteria, Pageable pageable);
}
