package com.cleancode.ecommerce.product.infra.gateway;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import com.cleancode.ecommerce.product.infra.persistence.product.ProductEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductCategoryEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.TypeCoinEntity;
import java.math.BigDecimal;

public interface ProductJpa extends JpaRepository<ProductEntity, String> {

	Page<ProductEntity> findByActiveTrue(Pageable pageable);

	Page<ProductEntity> findByActiveFalse(Pageable pageable);

	Optional<ProductEntity> findById(String productId);

	@Query("select distinct p from ProductEntity p where "
			+ "p.active = true and "
			+ "(:id is null or p.product_id = :id) and "
			+ "(:name is null or lower(p.name) like lower(concat('%', :name, '%'))) and "
			+ "(:description is null or lower(p.description) like lower(concat('%', :description, '%'))) and "
			+ "(:minimumPrice is null or p.price >= :minimumPrice) and "
			+ "(:maximumPrice is null or p.price <= :maximumPrice) and "
			+ "(:typeCoin is null or p.type_coin = :typeCoin) and "
			+ "(:category is null or p.category = :category) and "
			+ "(:brand is null or lower(p.brand) like lower(concat('%', :brand, '%'))) and "
			+ "(:minimumPricing is null or p.pricing >= :minimumPricing) and "
			+ "(:maximumPricing is null or p.pricing <= :maximumPricing) and "
			+ "(:mediaId is null or exists (select m from MidiaEntity m where m.product = p and m.midia_id = :mediaId)) and "
			+ "(:mediaUrl is null or exists (select m from MidiaEntity m where m.product = p and lower(m.url) like lower(concat('%', :mediaUrl, '%')))) and "
			+ "(:mediaDescription is null or exists (select m from MidiaEntity m where m.product = p and lower(m.description) like lower(concat('%', :mediaDescription, '%'))))")
	Page<ProductEntity> search(
			@Param("id") String id,
			@Param("name") String name,
			@Param("description") String description,
			@Param("minimumPrice") BigDecimal minimumPrice,
			@Param("maximumPrice") BigDecimal maximumPrice,
			@Param("typeCoin") TypeCoinEntity typeCoin,
			@Param("category") ProductCategoryEntity category,
			@Param("brand") String brand,
			@Param("mediaId") String mediaId,
			@Param("mediaUrl") String mediaUrl,
			@Param("mediaDescription") String mediaDescription,
			@Param("minimumPricing") BigDecimal minimumPricing,
			@Param("maximumPricing") BigDecimal maximumPricing,
			Pageable pageable);

}
