package com.cleancode.ecommerce.wishlist.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.wishlist.application.usecase.WishlistService;
import com.cleancode.ecommerce.wishlist.application.usecase.WishlistServiceImpl;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

@Configuration
public class WishlistConfig {

	@Bean
	public WishlistService wishlistService(CustomerRepository customerRepository, WishlistRepository wishlistRepository) {
		return new WishlistServiceImpl(customerRepository, wishlistRepository);
	}
}
