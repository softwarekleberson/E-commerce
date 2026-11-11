package com.cleancode.ecommerce.wishlist.application.usecase;

import java.util.List;

import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.wishlist.application.dto.WishlistItemDto;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

public class WishlistServiceImpl implements WishlistService {

	private final CustomerRepository customerRepository;
	private final WishlistRepository wishlistRepository;

	public WishlistServiceImpl(CustomerRepository customerRepository, WishlistRepository wishlistRepository) {
		this.customerRepository = customerRepository;
		this.wishlistRepository = wishlistRepository;
	}

	@Override
	public List<WishlistItemDto> list(String email) {
		String customerId = resolveCustomerId(email);
		return wishlistRepository.findByCustomerId(customerId).stream()
				.map(item -> new WishlistItemDto(item.getProductId(), item.getAddedAt()))
				.toList();
	}

	@Override
	public void remove(String email, String productId) {
		wishlistRepository.deleteByCustomerIdAndProductId(resolveCustomerId(email), productId);
	}

	private String resolveCustomerId(String email) {
		return customerRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalDomainException("Customer not found with email: " + email))
				.getId().getValue();
	}
}
