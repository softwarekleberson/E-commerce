
package com.cleancode.ecommerce.cart.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tb_cart")
public class CartEntity {

	@Id
	private String cart_id;
	private String customer_id;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CartItemEntity> cart_itens = new ArrayList<>();
	
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private BigDecimal total_price;
	
	@Enumerated(EnumType.STRING)
	private TypeCoinEntity coin;

	@Override
	public int hashCode() {
		return Objects.hash(cart_id, cart_itens, coin, created_at, customer_id, total_price, updated_at);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartEntity other = (CartEntity) obj;
		return Objects.equals(cart_id, other.cart_id) && Objects.equals(cart_itens, other.cart_itens)
				&& coin == other.coin && Objects.equals(created_at, other.created_at)
				&& Objects.equals(customer_id, other.customer_id) && Objects.equals(total_price, other.total_price)
				&& Objects.equals(updated_at, other.updated_at);
	}
}
