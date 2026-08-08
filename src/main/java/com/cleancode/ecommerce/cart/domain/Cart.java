package com.cleancode.ecommerce.cart.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.cleancode.ecommerce.cart.domain.exception.IllegalCartException;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.shared.kernel.UrlProduct;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class Cart {

	private final CartId cartId;
	private final CustomerId customerId;
	private final List<CartItem> cartItens;
	private final LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Price totalPrice;

	public Cart(CartId id, CustomerId customerId) {
		if (customerId == null) {
			throw new IllegalDomainException("Customer ID cannot be null");
		}

		this.cartId = id;
		this.customerId = customerId;
		this.cartItens = new ArrayList<>();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		recalculateTotalPrice();
	}

	public Cart(CartId cartId, CustomerId customerId, List<CartItem> cartItens, LocalDateTime createdAt,
			LocalDateTime updatedAt) {

		if (cartId == null || customerId == null) {
			throw new IllegalCartException("Cart ID and Customer ID cannot be null");
		}

		this.cartId = cartId;
		this.customerId = customerId;
		this.cartItens = new ArrayList<>(cartItens != null ? cartItens : new ArrayList<>());
		this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
		this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
		recalculateTotalPrice();
	}

	private void recalculateTotalPrice() {
		BigDecimal total = cartItens.stream().map(item -> item.getSubtotal().getPrice()).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		TypeCoin coin = cartItens.isEmpty() ? TypeCoin.DOLAR : cartItens.get(0).getUnitPrice().getCoin();

		this.totalPrice = new Price(total, coin);
	}

	public void addProductToCart(CartItemId cartItemId, ProductId productId, Name name, UrlProduct urlProduct ,Quantity quantity,
			Price unitPrice, ReservationId reservationId) {

		if (productId == null || name == null || quantity == null || unitPrice == null) {
			throw new IllegalCartException("Product data cannot be null");
		}

		boolean exists = cartItens.stream().anyMatch(item -> item.getCartItemId().equals(cartItemId));

		if (exists) {
			throw new IllegalCartException("Product already in cart. Use changeProductQuantity instead.");
		}

		cartItens.add(new CartItem(cartItemId, productId, name, urlProduct, quantity, unitPrice, reservationId));

		recalculateTotalPrice();
		this.updatedAt = LocalDateTime.now();
	}

	public void changeProductQuantity(CartItemId cartItemId, Quantity newQuantity, ReservationId newReservationId) {
	    if (cartItemId == null || newQuantity == null || newReservationId == null) {
	        throw new IllegalCartException("Product ID, quantity, and reservation ID cannot be null");
	    }

	    CartItem item = findItemByCartItem(cartItemId); 
	    item.changeQuantity(newQuantity);
	    
	    item.changeReservationId(newReservationId); 

	    recalculateTotalPrice();
	    this.updatedAt = LocalDateTime.now();
	}

	public void removeAllProducts() {
		cartItens.clear();
		recalculateTotalPrice();
		this.updatedAt = LocalDateTime.now();
	}

	public void removeProductFromCart(CartItemId cartItemId) {
		if (cartItemId == null) {
			throw new IllegalCartException("Product ID cannot be null");
		}

		boolean removed = cartItens.removeIf(c -> c.getCartItemId().equals(cartItemId));

		if (!removed) {
			throw new IllegalCartException("Product not found in cart");
		}

		recalculateTotalPrice();
		this.updatedAt = LocalDateTime.now();
	}

	private CartItem findItemByCartItem(CartItemId cartItemId) {
		return cartItens.stream().filter(c -> c.getCartItemId().equals(cartItemId)).findFirst()
				.orElseThrow(() -> new IllegalCartException("Not found item by cart Item"));
	}

	public CartId getCartId() {
		return cartId;
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public List<CartItem> getCartItens() {
		return Collections.unmodifiableList(this.cartItens);
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Price getTotalPrice() {
		return totalPrice;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cartId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return Objects.equals(cartId, other.cartId);
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", customerId=" + customerId + ", cartItens=" + cartItens + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", totalPrice=" + totalPrice + "]";
	}
}
