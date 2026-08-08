package com.cleancode.ecommerce.adm.domain.management;

import com.cleancode.ecommerce.adm.domain.repository.AdmRepository;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;

public class RemoveProductCart {

	private final AdmRepository admRepository;
	private final CartRepository cartRepository;
	
	public RemoveProductCart(AdmRepository admRepository, CartRepository cartRepository) {
		this.admRepository = admRepository;
		this.cartRepository = cartRepository;
	}
	
	
}
