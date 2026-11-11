package com.cleancode.ecommerce.cart.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartReservationTimeout;
import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartId;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.shared.kernel.UrlProduct;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;
import com.cleancode.ecommerce.wishlist.domain.WishlistItem;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

@ExtendWith(MockitoExtension.class)
class ExpireCartReservationsImplTest {

	@Mock
	private CartRepository cartRepository;
	@Mock
	private CartRemovalIntegrationService removalService;
	@Mock
	private WishlistRepository wishlistRepository;
	@Mock
	private CartReservationTimeout reservationTimeout;
	@Mock
	private Name productName;

	@Test
	void releasesReservationsAndMovesExpiredCartItemsToWishlist() {
		Cart cart = new Cart(new CartId("cart-1"), new CustomerId("customer-1"));
		cart.addProductToCart(new CartItemId("item-1"), new ProductId("product-1"), productName,
				new UrlProduct("https://example.test/product.png"), new Quantity(2),
				new Price(new BigDecimal("12.00"), TypeCoin.REAL), new ReservationId("reservation-1"));
		when(reservationTimeout.getMaxDurationMinutes()).thenReturn(30);
		when(cartRepository.findExpiredCarts(any(LocalDateTime.class))).thenReturn(List.of(cart));
		when(cartRepository.save(cart)).thenReturn(cart);

		int expiredCount = new ExpireCartReservationsImpl(cartRepository, removalService, wishlistRepository,
				reservationTimeout).execute();

		assertThat(expiredCount).isEqualTo(1);
		assertThat(cart.getCartItens()).isEmpty();
		verify(removalService).releaseStockReservation("reservation-1");
		ArgumentCaptor<WishlistItem> wishlistItem = ArgumentCaptor.forClass(WishlistItem.class);
		verify(wishlistRepository).save(wishlistItem.capture());
		assertThat(wishlistItem.getValue().getCustomerId()).isEqualTo("customer-1");
		assertThat(wishlistItem.getValue().getProductId()).isEqualTo("product-1");
		verify(cartRepository).save(cart);
	}
}
