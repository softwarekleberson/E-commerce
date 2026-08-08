package com.cleancode.ecommerce.cart.infra.persistence;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cart_item")
@Entity
public class CartItemEntity {

	@Id
	private String cart_item_id;

	private String product_id;
	private String product_name;
	private String url_product;
	private int quantity;
	private BigDecimal unit_price;

	@Enumerated(EnumType.STRING)
	private TypeCoinEntity coin;

	private BigDecimal subtotal;
	
	private String reservation_id;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private CartEntity cart;

	@Override
	public int hashCode() {
		return Objects.hash(cart, cart_item_id, product_id, product_name, quantity, coin, unit_price, subtotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		CartItemEntity other = (CartItemEntity) obj;
		return quantity == other.quantity && Objects.equals(cart, other.cart)
				&& Objects.equals(cart_item_id, other.cart_item_id) && Objects.equals(product_id, other.product_id)
				&& Objects.equals(product_name, other.product_name) && Objects.equals(unit_price, other.unit_price)
				&& Objects.equals(subtotal, other.subtotal) && coin == other.coin;
	}
}