package com.cleancode.ecommerce.cart.application.usecase;

import com.cleancode.ecommerce.cart.application.dtos.input.UpdateCartDto;
import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartShippingCostCalculator;
import com.cleancode.ecommerce.cart.application.service.contract.CartUpdateIntegrationService;
import com.cleancode.ecommerce.cart.application.usecase.contract.UpdateCart;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.shared.kernel.domain.Quantity;
import com.cleancode.ecommerce.shared.kernel.domain.ReservationId;

public class UpdateCartImpl implements UpdateCart {

	private final CartRepository cartRepository;
	private final CartUpdateIntegrationService updateService; 
	private final CartShippingCostCalculator shippingCostCalculator;

	public UpdateCartImpl(CartRepository cartRepository, CartUpdateIntegrationService updateService,
			CartShippingCostCalculator shippingCostCalculator) {
		this.cartRepository = cartRepository;
		this.updateService = updateService;
		this.shippingCostCalculator = shippingCostCalculator;
	}

	@Override
	public CartDto execute(String email, String cartItemId, UpdateCartDto dto) {

		String customerId = updateService.resolveCustomerIdByEmail(email);

		Cart cart = cartRepository.getCartCustomer(customerId).orElseThrow(
				() -> new IllegalArgumentException("Cart not found for customer ID: " + customerId));

		var stockResult = updateService.cycleProductReservation(
				dto.reservationId(), 
				dto.quantity(), 
				customerId, 
				cart.getCartId().getCartId()
		);

		ReservationId novoReservationId = new ReservationId(stockResult.newReservationId());
		cart.changeProductQuantity(new CartItemId(cartItemId), new Quantity(dto.quantity()), novoReservationId);

		cartRepository.save(cart);

		return new CartDto(cart, shippingCostCalculator.calculate(cart.getTotalPrice().getPrice()));
	}	
}