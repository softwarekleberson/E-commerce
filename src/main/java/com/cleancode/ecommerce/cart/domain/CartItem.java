package com.cleancode.ecommerce.cart.domain;

import java.math.BigDecimal;
import java.util.Objects;

import com.cleancode.ecommerce.cart.domain.exception.IllegalCartException;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.UrlProduct;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class CartItem {

	private final CartItemId cartItemId;
    private final ProductId productId;
    private final Name productName;
    private final UrlProduct urlProduct;
    private Quantity quantity;  
    private final Price unitPrice;
    private ReservationId reservationId;

    public CartItem(CartItemId cartItemId, ProductId productId, Name productName, UrlProduct urlProduct, Quantity quantity, Price unitPrice, ReservationId reservationId) {
        if (cartItemId == null || productId == null || productName == null || urlProduct == null || quantity == null || unitPrice == null || reservationId == null) {
            throw new IllegalArgumentException("Product data cannot be null");
        }

        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.urlProduct = urlProduct;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.reservationId = reservationId;
    }

    public Price calculateSubtotal() {
        BigDecimal total = this.unitPrice.getPrice()
                .multiply(BigDecimal.valueOf(this.quantity.getQuantity()));
        return new Price(total, this.unitPrice.getCoin());
    }

    public void increaseQuantity(Quantity additional) {
        if (additional.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive");
        }
        this.quantity = new Quantity(this.quantity.getQuantity() + additional.getQuantity());
    }

    public void changeQuantity(Quantity newQuantity) {
        if (newQuantity.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = newQuantity;
    }
    
    public void changeReservationId(ReservationId newReservationId) {
	    if (newReservationId == null) {
	        throw new IllegalCartException("Reservation ID cannot be null");
	    }
	    this.reservationId = newReservationId;
	}
    
    public CartItemId getCartItemId() {
		return cartItemId;
	}

    public ProductId getProductId() {
        return productId;
    }
    
    public String getReservationId() {
		return reservationId.getReservationId();
	}

    public Name getProductName() {
        return productName;
    }
    
    public UrlProduct getUrlProduct() {
		return urlProduct;
	}

    public Quantity getQuantity() {
        return quantity;
    }

    public Price getUnitPrice() {
        return unitPrice;
    }

    public Price getSubtotal() {
        return calculateSubtotal();
    }

	@Override
	public int hashCode() {
		return Objects.hash(cartItemId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return Objects.equals(cartItemId, other.cartItemId);
	}

}