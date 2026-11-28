package com.cleancode.ecommerce.wishlist.infra.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_wishlist_item")
public class WishlistItemEntity {

	@Id
	private String wishlist_item_id;

	private String customer_id;
	private String product_id;
	private LocalDateTime added_at;
}
