package com.cleancode.ecommerce.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.CartItem;
import com.cleancode.ecommerce.cart.domain.exception.IllegalCartException;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.shared.kernel.UrlProduct;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

@ExtendWith(MockitoExtension.class)
class CartItemTest {

    private CartItemId defaultCartItemId;
    private ProductId defaultProductId;
    private UrlProduct defaultUrlProduct;
    private Quantity defaultQuantity;
    private Price defaultUnitPrice;
    private ReservationId defaultReservationId;

    @Mock
    private Name nameMock; // Dummy mock to satisfy Name reference compilation

    @BeforeEach
    void setUp() {
        defaultCartItemId = new CartItemId();
        defaultProductId = new ProductId();
        defaultUrlProduct = new UrlProduct("https://cleancode.com/product.jpg");
        defaultQuantity = new Quantity(2);
        // Using TypeCoin.REAL which we previously established in the package mapping
        defaultUnitPrice = new Price(new BigDecimal("50.00"), TypeCoin.REAL);
        defaultReservationId = new ReservationId("res-123");
    }

    @Test
    @DisplayName("Should successfully construct CartItens instance when all parameters are non-null")
    void shouldConstructCartItemSuccessfully() {
        CartItem cartItem = new CartItem(
                defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, 
                defaultQuantity, defaultUnitPrice, defaultReservationId
        );

        assertThat(cartItem.getCartItemId()).isEqualTo(defaultCartItemId);
        assertThat(cartItem.getProductId()).isEqualTo(defaultProductId);
        assertThat(cartItem.getProductName()).isEqualTo(nameMock);
        assertThat(cartItem.getUrlProduct()).isEqualTo(defaultUrlProduct);
        assertThat(cartItem.getQuantity().getQuantity()).isEqualTo(2);
        assertThat(cartItem.getUnitPrice()).isEqualTo(defaultUnitPrice);
        assertThat(cartItem.getReservationId()).isEqualTo("res-123");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when any required constructor parameter is missing")
    void shouldThrowExceptionWhenAnyConstructorParameterIsNull() {
        assertThatThrownBy(() -> new CartItem(null, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultUnitPrice, defaultReservationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product data cannot be null");

        assertThatThrownBy(() -> new CartItem(defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultUnitPrice, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product data cannot be null");
    }

    @Test
    @DisplayName("Should calculate accurate subtotal by multiplying unit price by line item quantity")
    void shouldCalculateSubtotalCorrectly() {
        CartItem cartItem = new CartItem(
                defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, 
                defaultQuantity, defaultUnitPrice, defaultReservationId
        );

        // 50.00 * 2 = 100.00
        Price calculatedSubtotal = cartItem.calculateSubtotal();
        Price getterSubtotal = cartItem.getSubtotal();

        assertThat(calculatedSubtotal.getPrice()).isEqualByComparingTo("100.00");
        assertThat(calculatedSubtotal.getCoin()).isEqualTo(TypeCoin.REAL);
        assertThat(getterSubtotal.getPrice()).isEqualByComparingTo("100.00");
    }

    @Test
    @DisplayName("Should update internal quantity balance upwards when increasing quantity by a positive amount")
    void shouldIncreaseQuantitySuccessfully() {
        CartItem cartItem = new CartItem(
                defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, 
                defaultQuantity, defaultUnitPrice, defaultReservationId
        );

        cartItem.increaseQuantity(new Quantity(3));

        assertThat(cartItem.getQuantity().getQuantity()).isEqualTo(5); // 2 + 3 = 5
    }

    @Test
    @DisplayName("Should update internal quantity reference directly when changeQuantity receives a valid adjustment")
    void shouldChangeQuantitySuccessfully() {
        CartItem cartItem = new CartItem(
                defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, 
                defaultQuantity, defaultUnitPrice, defaultReservationId
        );

        cartItem.changeQuantity(new Quantity(10));

        assertThat(cartItem.getQuantity().getQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should successfully overwrite reservation configuration with a fresh target instance")
    void shouldChangeReservationIdSuccessfully() {
        CartItem cartItem = new CartItem(
                defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, 
                defaultQuantity, defaultUnitPrice, defaultReservationId
        );
        ReservationId secondaryReservationId = new ReservationId("res-999");

        cartItem.changeReservationId(secondaryReservationId);

        assertThat(cartItem.getReservationId()).isEqualTo("res-999");
    }

    @Test
    @DisplayName("Should throw IllegalCartException when trying to assign a null reference via changeReservationId")
    void shouldThrowExceptionWhenChangingReservationToNull() {
        CartItem cartItem = new CartItem(
                defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, 
                defaultQuantity, defaultUnitPrice, defaultReservationId
        );

        assertThatThrownBy(() -> cartItem.changeReservationId(null))
                .isInstanceOf(IllegalCartException.class)
                .hasMessage("Reservation ID cannot be null");
    }

    @Test
    @DisplayName("Should guarantee that structural equality (equals and hashCode) matches strictly based on the CartItemId property")
    void shouldRespectEqualsAndHashCodeContractBasedOnCartItemIdOnly() {
        CartItem cartItem1 = new CartItem(
                defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, 
                defaultQuantity, defaultUnitPrice, defaultReservationId
        );
        
        // Creating an completely different item but utilizing the exact same CartItemId instance
        CartItem cartItem2 = new CartItem(
                defaultCartItemId, new ProductId("different"), nameMock, defaultUrlProduct, 
                new Quantity(99), defaultUnitPrice, new ReservationId("other-res")
        );

        CartItem differentCartItem = new CartItem(
                new CartItemId("other-cart-item-id"), defaultProductId, nameMock, defaultUrlProduct, 
                defaultQuantity, defaultUnitPrice, defaultReservationId
        );

        // Equals validations
        assertThat(cartItem1).isEqualTo(cartItem2);
        assertThat(cartItem1).isNotEqualTo(differentCartItem);
        assertThat(cartItem1).isNotEqualTo(null);

        // HashCode validations
        assertThat(cartItem1.hashCode()).isEqualTo(cartItem2.hashCode());
        assertThat(cartItem1.hashCode()).isNotEqualTo(differentCartItem.hashCode());
    }
}