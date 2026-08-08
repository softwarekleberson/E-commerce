package com.cleancode.ecommerce.cart.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.cart.application.dtos.input.CreateCartDto;
import com.cleancode.ecommerce.cart.application.dtos.input.DeleteUniqueProductToCartDto;
import com.cleancode.ecommerce.cart.application.dtos.input.UpdateCartDto;
import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;
import com.cleancode.ecommerce.cart.application.usecase.contract.AddProductToCart;
import com.cleancode.ecommerce.cart.application.usecase.contract.DeleteAllCart;
import com.cleancode.ecommerce.cart.application.usecase.contract.DeleteUniqueProductCart;
import com.cleancode.ecommerce.cart.application.usecase.contract.ListCart;
import com.cleancode.ecommerce.cart.application.usecase.contract.UpdateCart;

import jakarta.validation.Valid;

@RestController
@RequestMapping("customer/cart")
@CrossOrigin(origins = "*")
public class CartController {

	private final AddProductToCart addProductToCart;
	private final ListCart listCart;
	private final UpdateCart updateCart;
	private final DeleteAllCart deleteAllCart;
	private final DeleteUniqueProductCart deleteUniqueProductCart;

	public CartController(AddProductToCart addProductToCart, ListCart listCart, UpdateCart updateCart, DeleteAllCart deleteAllCart, DeleteUniqueProductCart deleteUniqueProductCart) {
		this.addProductToCart = addProductToCart;
		this.listCart = listCart;
		this.updateCart = updateCart;
		this.deleteAllCart = deleteAllCart;
		this.deleteUniqueProductCart = deleteUniqueProductCart;
	}

	@PostMapping
	public ResponseEntity<CartDto> createCart(Authentication authentication, @Valid @RequestBody CreateCartDto dto) {
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		dto.setEmail(email);
		
		var create = addProductToCart.execute(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(create);
	}

	@GetMapping
	public ResponseEntity<CartDto> getCart(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		
		return ResponseEntity.ok(listCart.execute(email));
	}

	@PutMapping("/update/{cartItemId}")
	public ResponseEntity<CartDto> updateCart(Authentication authentication, @PathVariable String cartItemId, @Valid @RequestBody UpdateCartDto dto) {
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		
		var update = updateCart.execute(email, cartItemId, dto);
		return ResponseEntity.ok(update);
	}
	
	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<Void> deleteProductCart(Authentication authentication, @PathVariable String cartItemId, @Valid @RequestBody DeleteUniqueProductToCartDto dto) {
		
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		
		deleteUniqueProductCart.execute(email, cartItemId,dto);
		return ResponseEntity.noContent().build();
	}
}