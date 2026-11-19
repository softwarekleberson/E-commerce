package com.cleancode.ecommerce.cart.application.usecase;

import java.util.UUID;

import com.cleancode.ecommerce.cart.application.dtos.input.CreateCartDto;
import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartCatalogIntegrationService;
import com.cleancode.ecommerce.cart.application.service.dto.ProductReservationResult;
import com.cleancode.ecommerce.cart.application.usecase.contract.AddProductToCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.order.domain.shipping.ShippingCostStrategyFactory;
import com.cleancode.ecommerce.shared.kernel.domain.CartId;
import com.cleancode.ecommerce.shared.kernel.domain.CustomerId;
import com.cleancode.ecommerce.shared.kernel.domain.Name;
import com.cleancode.ecommerce.shared.kernel.domain.Price;
import com.cleancode.ecommerce.shared.kernel.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.domain.Quantity;
import com.cleancode.ecommerce.shared.kernel.domain.ReservationId;
import com.cleancode.ecommerce.shared.kernel.domain.UrlProduct;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

public class AddProductToCartImpl implements AddProductToCart {

	private final CartRepository cartRepository;
	private final CartCatalogIntegrationService integrationService; 
	private final ShippingCostStrategyFactory shippingStrategyFactory;
	private final WishlistRepository wishlistRepository;

	public AddProductToCartImpl(CartRepository cartRepository, CartCatalogIntegrationService integrationService,
			ShippingCostStrategyFactory shippingStrategyFactory, WishlistRepository wishlistRepository) {
		this.cartRepository = cartRepository;
		this.integrationService = integrationService;
		this.shippingStrategyFactory = shippingStrategyFactory;
		this.wishlistRepository = wishlistRepository;
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
		wishlistRepository.deleteByCustomerIdAndProductId(contextDetails.customerId(), contextDetails.productId());
		return new CartDto(cart, shippingStrategyFactory.calculate(cart.getTotalPrice().getPrice()));
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