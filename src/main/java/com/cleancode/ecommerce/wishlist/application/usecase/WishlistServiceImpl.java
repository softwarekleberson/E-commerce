package com.cleancode.ecommerce.wishlist.application.usecase;

import java.util.List;

import com.cleancode.ecommerce.shared.kernel.exception.CustomerNotFoundException;
import com.cleancode.ecommerce.shared.kernel.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.wishlist.application.dto.WishlistItemDto;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

public class WishlistServiceImpl implements WishlistService {

	private final CustomerIdentityIdService customerIdentityIdService;
	private final WishlistRepository wishlistRepository;

	public WishlistServiceImpl(CustomerIdentityIdService customerRepository, WishlistRepository wishlistRepository) {
		this.customerIdentityIdService = customerRepository;
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
		return customerIdentityIdService.customerIdentityIdService(email)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email))
				;
	}
}
