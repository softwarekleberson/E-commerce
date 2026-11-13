package com.cleancode.ecommerce.cart.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.cleancode.ecommerce.cart.domain.Cart;

public interface CartRepository {

	Cart save (Cart cart);
	Optional <Cart> getCartCustomer(String customerId);
	List<Cart> findExpiredCarts(LocalDateTime cutoff);
}
