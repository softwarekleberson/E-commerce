package com.cleancode.ecommerce.cart.application.usecase;

import java.util.UUID;

import com.cleancode.ecommerce.cart.application.dtos.input.CreateCartDto;
import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartCatalogIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartShippingCostCalculator;
import com.cleancode.ecommerce.cart.application.service.dto.ProductReservationResult;
import com.cleancode.ecommerce.cart.application.usecase.contract.AddProductToCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.event.cart.ProductAddedToCartEvent;
import com.cleancode.ecommerce.event.cart.ProductAddedToCartEventPublisher;
import com.cleancode.ecommerce.shared.kernel.domain.CartId;
import com.cleancode.ecommerce.shared.kernel.domain.CustomerId;
import com.cleancode.ecommerce.shared.kernel.domain.Name;
import com.cleancode.ecommerce.shared.kernel.domain.Price;
import com.cleancode.ecommerce.shared.kernel.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.domain.Quantity;
import com.cleancode.ecommerce.shared.kernel.domain.ReservationId;
import com.cleancode.ecommerce.shared.kernel.domain.UrlProduct;

public class AddProductToCartImpl implements AddProductToCart {

	private final CartRepository cartRepository;
	private final CartCatalogIntegrationService integrationService; 
	private final CartShippingCostCalculator shippingCostCalculator;
	private final ProductAddedToCartEventPublisher productAddedToCartEventPublisher;

	public AddProductToCartImpl(CartRepository cartRepository, CartCatalogIntegrationService integrationService,
			CartShippingCostCalculator shippingCostCalculator,
			ProductAddedToCartEventPublisher productAddedToCartEventPublisher) {
		this.cartRepository = cartRepository;
		this.integrationService = integrationService;
		this.shippingCostCalculator = shippingCostCalculator;
		this.productAddedToCartEventPublisher = productAddedToCartEventPublisher;
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
		productAddedToCartEventPublisher.publish(
				new ProductAddedToCartEvent(contextDetails.customerId(), contextDetails.productId()));
		return new CartDto(cart, shippingCostCalculator.calculate(cart.getTotalPrice().getPrice()));
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