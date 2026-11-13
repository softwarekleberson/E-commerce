package com.cleancode.ecommerce.wishlist.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.wishlist.application.dto.WishlistItemDto;
import com.cleancode.ecommerce.wishlist.application.usecase.WishlistService;

import java.util.List;

@RestController
@RequestMapping("/customer/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

	private final WishlistService wishlistService;

	public WishlistController(WishlistService wishlistService) {
		this.wishlistService = wishlistService;
	}

	@GetMapping
	public ResponseEntity<List<WishlistItemDto>> list(Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(wishlistService.list(authentication.getName()));
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> remove(Authentication authentication, @PathVariable String productId) {
		if (!isAuthenticated(authentication)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		wishlistService.remove(authentication.getName(), productId);
		return ResponseEntity.noContent().build();
	}

	private boolean isAuthenticated(Authentication authentication) {
		return authentication != null && authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken);
	}
}
