package com.cleancode.ecommerce.cart.infra.gateway;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.cart.infra.mapper.CartMapper;
import com.cleancode.ecommerce.cart.infra.persistence.CartEntity;

@Repository
public class CartRepositoryJpa implements CartRepository {

	private final CartJpa cartJpa;

	public CartRepositoryJpa(CartJpa cartJpa) {
		this.cartJpa = cartJpa;
	}

	@Transactional
	@Override
	public Cart save(Cart cart) {
		Optional<CartEntity> optionalEntity = cartJpa.findByCustomerId(cart.getCustomerId().getValue());
		CartEntity entity;

		if (optionalEntity.isPresent()) {
			entity = CartMapper.toEntity(cart, optionalEntity.get()); 
		} else {
			entity = CartMapper.toEntity(cart);
		}

		cartJpa.save(entity);
		return CartMapper.toDomain(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Cart> getCartCustomer(String customerId) {
		return cartJpa.findByCustomerId(customerId).map(CartMapper::toDomain);
	}
}