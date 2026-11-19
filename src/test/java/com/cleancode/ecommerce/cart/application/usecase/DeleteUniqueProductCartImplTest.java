package com.cleancode.ecommerce.cart.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.cart.application.dtos.input.DeleteUniqueProductToCartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.event.cart.ProductRemovedFromCartEvent;
import com.cleancode.ecommerce.event.cart.ProductRemovedFromCartEventPublisher;
import com.cleancode.ecommerce.shared.kernel.domain.CartId;
import com.cleancode.ecommerce.shared.kernel.domain.CustomerId;
import com.cleancode.ecommerce.shared.kernel.domain.Name;
import com.cleancode.ecommerce.shared.kernel.domain.Price;
import com.cleancode.ecommerce.shared.kernel.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.domain.Quantity;
import com.cleancode.ecommerce.shared.kernel.domain.ReservationId;
import com.cleancode.ecommerce.shared.kernel.domain.TypeCoin;
import com.cleancode.ecommerce.shared.kernel.domain.UrlProduct;
import com.cleancode.ecommerce.shared.kernel.exception.IllegalDomainException;

@ExtendWith(MockitoExtension.class)
class DeleteUniqueProductCartImplTest {

	@Mock
	private CartRepository cartRepository;
	@Mock
	private CartRemovalIntegrationService removalService;
	@Mock
	private ProductRemovedFromCartEventPublisher eventPublisher;

	@Test
	void cancelsReservationAndPublishesProductRemovedEvent() {
		Cart cart = createCart();
		when(removalService.resolveCustomerIdByEmail("customer@example.test")).thenReturn("customer-1");
		when(cartRepository.getCartCustomer("customer-1")).thenReturn(Optional.of(cart));

		new DeleteUniqueProductCartImpl(cartRepository, removalService, eventPublisher).execute(
				"customer@example.test", "item-1", new DeleteUniqueProductToCartDto("reservation-1"));

		assertThat(cart.getCartItens()).isEmpty();
		verify(removalService).releaseStockReservation("reservation-1");
		verify(cartRepository).save(cart);
		verify(eventPublisher).publish(new ProductRemovedFromCartEvent("customer-1", "product-1"));
	}

	@Test
	void rejectsAReservationThatDoesNotMatchTheCartItem() {
		Cart cart = createCart();
		when(removalService.resolveCustomerIdByEmail("customer@example.test")).thenReturn("customer-1");
		when(cartRepository.getCartCustomer("customer-1")).thenReturn(Optional.of(cart));

		assertThatThrownBy(() -> new DeleteUniqueProductCartImpl(cartRepository, removalService, eventPublisher)
				.execute("customer@example.test", "item-1", new DeleteUniqueProductToCartDto("other-reservation")))
				.isInstanceOf(IllegalDomainException.class)
				.hasMessage("Reservation does not belong to the cart item");

		verify(removalService, never()).releaseStockReservation(any());
		verify(cartRepository, never()).save(any(Cart.class));
		verify(eventPublisher, never()).publish(any());
	}

	private Cart createCart() {
		Cart cart = new Cart(new CartId("cart-1"), new CustomerId("customer-1"));
		cart.addProductToCart(new CartItemId("item-1"), new ProductId("product-1"), new Name("Product"),
				new UrlProduct("https://example.test/product.png"), new Quantity(1),
				new Price(new BigDecimal("10.00"), TypeCoin.REAL), new ReservationId("reservation-1"));
		return cart;
	}
}
