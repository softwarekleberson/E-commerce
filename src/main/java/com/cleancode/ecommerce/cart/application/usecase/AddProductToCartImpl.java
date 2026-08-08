package com.cleancode.ecommerce.cart.application.usecase;

import java.util.UUID;

import com.cleancode.ecommerce.cart.application.dtos.input.CreateCartDto;
import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartCatalogIntegrationService;
import com.cleancode.ecommerce.cart.application.service.dto.ProductReservationResult;
import com.cleancode.ecommerce.cart.application.usecase.contract.AddProductToCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartId;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.UrlProduct;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class AddProductToCartImpl implements AddProductToCart {

	private final CartRepository cartRepository;
	private final CartCatalogIntegrationService integrationService; 

	public AddProductToCartImpl(CartRepository cartRepository, CartCatalogIntegrationService integrationService) {
		this.cartRepository = cartRepository;
		this.integrationService = integrationService;
	}

	@Override
	public CartDto execute(CreateCartDto dto) {
		
		var contextDetails = integrationService.resolveContextDetails(dto.getEmail(), dto.getProductId());
		Cart cart = getCartOrCreate(contextDetails.customerId());

		ProductReservationResult reservation = integrationService.reserveStock(
				dto.getProductId(), 
				dto.getQuantity(), 
				contextDetails.customerId(), 
				cart.getCartId().getCartId()
		);
		
		cart.addProductToCart(
				new CartItemId(),
				new ProductId(contextDetails.productId()),
				new Name(contextDetails.productName()),
				new UrlProduct(contextDetails.productImageUrl()),
				new Quantity(dto.getQuantity()),
				new Price(contextDetails.price(), contextDetails.coin()),
				new ReservationId(reservation.reservationId())
		);

		cartRepository.save(cart);
		return new CartDto(cart);
	}

	private Cart getCartOrCreate(String customerId) {
		return cartRepository.getCartCustomer(customerId)
				.orElseGet(() -> {
					Cart newCart = new Cart(new CartId(UUID.randomUUID().toString()), new CustomerId(customerId));
					cartRepository.save(newCart);
					return newCart;
				});
	}
}