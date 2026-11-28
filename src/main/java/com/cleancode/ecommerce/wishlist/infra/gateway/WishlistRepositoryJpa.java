package com.cleancode.ecommerce.wishlist.infra.gateway;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.wishlist.domain.WishlistItem;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;
import com.cleancode.ecommerce.wishlist.infra.persistence.WishlistItemEntity;

@Repository
public class WishlistRepositoryJpa implements WishlistRepository {

	private final WishlistItemJpa jpa;

	public WishlistRepositoryJpa(WishlistItemJpa jpa) {
		this.jpa = jpa;
	}

	@Transactional
	@Override
	public void save(WishlistItem item) {
		WishlistItemEntity entity = jpa.findByCustomerIdAndProductId(item.getCustomerId(), item.getProductId())
				.orElseGet(() -> new WishlistItemEntity(item.getId(), item.getCustomerId(), item.getProductId(),
						item.getAddedAt()));
		jpa.save(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public List<WishlistItem> findByCustomerId(String customerId) {
		return jpa.findByCustomerIdOrderByAddedAtDesc(customerId).stream()
				.map(entity -> new WishlistItem(entity.getWishlist_item_id(), entity.getCustomer_id(),
						entity.getProduct_id(), entity.getAdded_at()))
				.toList();
	}

	@Transactional
	@Override
	public void deleteByCustomerIdAndProductId(String customerId, String productId) {
		jpa.findByCustomerIdAndProductId(customerId, productId).ifPresent(jpa::delete);
	}
}
