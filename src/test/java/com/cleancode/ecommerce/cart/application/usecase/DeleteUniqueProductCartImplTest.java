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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.cart.application.dtos.input.DeleteUniqueProductToCartDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartId;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.shared.kernel.UrlProduct;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;
import com.cleancode.ecommerce.wishlist.domain.WishlistItem;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

@ExtendWith(MockitoExtension.class)
class DeleteUniqueProductCartImplTest {

	@Mock
	private CartRepository cartRepository;
	@Mock
	private CartRemovalIntegrationService removalService;
	@Mock
	private WishlistRepository wishlistRepository;

	@Test
	void cancelsReservationAndAddsRemovedProductToWishlist() {
		Cart cart = createCart();
		when(removalService.resolveCustomerIdByEmail("customer@example.test")).thenReturn("customer-1");
		when(cartRepository.getCartCustomer("customer-1")).thenReturn(Optional.of(cart));

		new DeleteUniqueProductCartImpl(cartRepository, removalService, wishlistRepository).execute(
				"customer@example.test", "item-1", new DeleteUniqueProductToCartDto("reservation-1"));

		assertThat(cart.getCartItens()).isEmpty();
		verify(removalService).releaseStockReservation("reservation-1");
		ArgumentCaptor<WishlistItem> wishlistItem = ArgumentCaptor.forClass(WishlistItem.class);
		verify(wishlistRepository).save(wishlistItem.capture());
		assertThat(wishlistItem.getValue().getCustomerId()).isEqualTo("customer-1");
		assertThat(wishlistItem.getValue().getProductId()).isEqualTo("product-1");
		verify(cartRepository).save(cart);
	}

	@Test
	void rejectsAReservationThatDoesNotMatchTheCartItem() {
		Cart cart = createCart();
		when(removalService.resolveCustomerIdByEmail("customer@example.test")).thenReturn("customer-1");
		when(cartRepository.getCartCustomer("customer-1")).thenReturn(Optional.of(cart));

		assertThatThrownBy(() -> new DeleteUniqueProductCartImpl(cartRepository, removalService, wishlistRepository)
				.execute("customer@example.test", "item-1", new DeleteUniqueProductToCartDto("other-reservation")))
				.isInstanceOf(IllegalDomainException.class)
				.hasMessage("Reservation does not belong to the cart item");

		verify(removalService, never()).releaseStockReservation(any());
		verify(wishlistRepository, never()).save(any());
		verify(cartRepository, never()).save(any(Cart.class));
	}

	private Cart createCart() {
		Cart cart = new Cart(new CartId("cart-1"), new CustomerId("customer-1"));
		cart.addProductToCart(new CartItemId("item-1"), new ProductId("product-1"), new Name("Product"),
				new UrlProduct("https://example.test/product.png"), new Quantity(1),
				new Price(new BigDecimal("10.00"), TypeCoin.REAL), new ReservationId("reservation-1"));
		return cart;
	}
}
