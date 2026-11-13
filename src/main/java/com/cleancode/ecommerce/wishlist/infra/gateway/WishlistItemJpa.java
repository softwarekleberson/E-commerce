package com.cleancode.ecommerce.wishlist.infra.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cleancode.ecommerce.wishlist.infra.persistence.WishlistItemEntity;

public interface WishlistItemJpa extends JpaRepository<WishlistItemEntity, String> {

	@Query("""
			SELECT item FROM WishlistItemEntity item
			WHERE item.customer_id = :customerId
			ORDER BY item.added_at DESC
			""")
	List<WishlistItemEntity> findByCustomerIdOrderByAddedAtDesc(@Param("customerId") String customerId);

	@Query("""
			SELECT item FROM WishlistItemEntity item
			WHERE item.customer_id = :customerId AND item.product_id = :productId
			""")
	Optional<WishlistItemEntity> findByCustomerIdAndProductId(@Param("customerId") String customerId,
			@Param("productId") String productId);
}
