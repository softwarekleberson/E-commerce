package com.cleancode.ecommerce.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartId;
import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.CartItem;
import com.cleancode.ecommerce.cart.domain.exception.IllegalCartException;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.shared.kernel.UrlProduct;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

@ExtendWith(MockitoExtension.class)
class CartTest {

    private CartId defaultCartId;
    private CustomerId defaultCustomerId;
    private CartItemId defaultCartItemId;
    private ProductId defaultProductId;
    private UrlProduct defaultUrlProduct;
    private Quantity defaultQuantity;
    private Price defaultPrice;
    private ReservationId defaultReservationId;

    @Mock
    private Name nameMock;

    @BeforeEach
    void setUp() {
        defaultCartId = new CartId("cart-123");
        defaultCustomerId = new CustomerId("123u");
        defaultCartItemId = new CartItemId("item-001");
        defaultProductId = new ProductId("prod-99");
        defaultUrlProduct = new UrlProduct("https://cleancode.com/img.png");
        defaultQuantity = new Quantity(2);
        defaultPrice = new Price(new BigDecimal("100.00"), TypeCoin.REAL);
        defaultReservationId = new ReservationId("res-abc");
    }

    @Test
    @DisplayName("Should initialize empty cart with base default structures using short constructor")
    void shouldConstructNewCartSuccessfully() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);

        assertThat(cart.getCartId()).isEqualTo(defaultCartId);
        assertThat(cart.getCustomerId()).isEqualTo(defaultCustomerId);
        assertThat(cart.getCartItens()).isEmpty();
        assertThat(cart.getTotalPrice().getPrice()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(cart.getTotalPrice().getCoin()).isEqualTo(TypeCoin.DOLAR); // Default coin fallback when empty
        assertThat(cart.getCreatedAt()).isNotNull();
        assertThat(cart.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should throw IllegalDomainException when short constructor gets null customer ID")
    void shouldThrowExceptionWhenNewCartCustomerIsNull() {
        assertThatThrownBy(() -> new Cart(defaultCartId, null))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Customer ID cannot be null");
    }

    @Test
    @DisplayName("Should reconstruct full aggregate and compute accurate totals using complete multi-arg constructor")
    void shouldReconstructCartSuccessfullyWithExistingItems() {
        List<CartItem> initialItems = new ArrayList<>();
        initialItems.add(new CartItem(defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultPrice, defaultReservationId));
        LocalDateTime past = LocalDateTime.now().minusDays(1);

        Cart cart = new Cart(defaultCartId, defaultCustomerId, initialItems, past, past);

        assertThat(cart.getCartId()).isEqualTo(defaultCartId);
        assertThat(cart.getCartItens()).hasSize(1);
        // 100.00 * 2 = 200.00 total price calculated
        assertThat(cart.getTotalPrice().getPrice()).isEqualByComparingTo("200.00");
        assertThat(cart.getTotalPrice().getCoin()).isEqualTo(TypeCoin.REAL);
        assertThat(cart.getCreatedAt()).isEqualTo(past);
    }

    @Test
    @DisplayName("Should throw IllegalCartException when full constructor has null identifier references")
    void shouldThrowExceptionWhenReconstructedIdsAreNull() {
        assertThatThrownBy(() -> new Cart(null, defaultCustomerId, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now()))
                .isInstanceOf(IllegalCartException.class)
                .hasMessage("Cart ID and Customer ID cannot be null");
    }

    @Test
    @DisplayName("Should seamlessly append product to list and increment aggregate total price")
    void shouldAddProductToCartSuccessfully() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);
        LocalDateTime initialUpdateTime = cart.getUpdatedAt();

        cart.addProductToCart(defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultPrice, defaultReservationId);

        assertThat(cart.getCartItens()).hasSize(1);
        assertThat(cart.getTotalPrice().getPrice()).isEqualByComparingTo("200.00");
        assertThat(cart.getTotalPrice().getCoin()).isEqualTo(TypeCoin.REAL);
        assertThat(cart.getUpdatedAt()).isAfterOrEqualTo(initialUpdateTime);
    }

    @Test
    @DisplayName("Should throw IllegalCartException when trying to add a product that matches an existing CartItemId")
    void shouldPreventDuplicateProductAddition() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);
        cart.addProductToCart(defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultPrice, defaultReservationId);

        assertThatThrownBy(() -> cart.addProductToCart(defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultPrice, defaultReservationId))
                .isInstanceOf(IllegalCartException.class)
                .hasMessage("Product already in cart. Use changeProductQuantity instead.");
    }

    @Test
    @DisplayName("Should update quantity and track changed reservation id when modifying an active item")
    void shouldModifyProductQuantityAndReservationSuccessfully() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);
        cart.addProductToCart(defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultPrice, defaultReservationId);

        Quantity updatedQuantity = new Quantity(5);
        ReservationId changedReservationId = new ReservationId("res-xyz");

        cart.changeProductQuantity(defaultCartItemId, updatedQuantity, changedReservationId);

        CartItem updatedItem = cart.getCartItens().get(0);
        assertThat(updatedItem.getQuantity().getQuantity()).isEqualTo(5);
        assertThat(updatedItem.getReservationId()).isEqualTo("res-xyz");
        // Subtotal verification: 100.00 * 5 = 500.00 total price
        assertThat(cart.getTotalPrice().getPrice()).isEqualByComparingTo("500.00");
    }

    @Test
    @DisplayName("Should throw IllegalCartException when changing quantity for an item not found in the collection")
    void shouldThrowExceptionWhenModifyingNonExistentItem() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);

        assertThatThrownBy(() -> cart.changeProductQuantity(new CartItemId("ghost-item"), new Quantity(5), defaultReservationId))
                .isInstanceOf(IllegalCartException.class)
                .hasMessage("Not found item by cart Item");
    }

    @Test
    @DisplayName("Should throw IllegalCartException when null values are supplied to changeProductQuantity parameters")
    void shouldValidationGuardAgainstNullsOnQuantityChanges() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);

        assertThatThrownBy(() -> cart.changeProductQuantity(null, defaultQuantity, defaultReservationId))
                .isInstanceOf(IllegalCartException.class)
                .hasMessage("Product ID, quantity, and reservation ID cannot be null");
    }

    @Test
    @DisplayName("Should wipe out the list collection entirely and drop subtotal sums back to zero")
    void shouldClearAllProductsFromCart() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);
        cart.addProductToCart(defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultPrice, defaultReservationId);

        cart.removeAllProducts();

        assertThat(cart.getCartItens()).isEmpty();
        assertThat(cart.getTotalPrice().getPrice()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should clean up and remove a target row precisely by its assigned item identity")
    void shouldRemoveSpecificItemSuccessfully() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);
        cart.addProductToCart(defaultCartItemId, defaultProductId, nameMock, defaultUrlProduct, defaultQuantity, defaultPrice, defaultReservationId);
        
        CartItemId fallbackItemId = new CartItemId("item-002");
        cart.addProductToCart(fallbackItemId, new ProductId("prod-2"), nameMock, defaultUrlProduct, new Quantity(1), defaultPrice, defaultReservationId);

        // Remove item 1
        cart.removeProductFromCart(defaultCartItemId);

        assertThat(cart.getCartItens()).hasSize(1);
        assertThat(cart.getCartItens().get(0).getCartItemId()).isEqualTo(fallbackItemId);
        // Total price recalculation verification: remaining item is 100.00 * 1 = 100.00
        assertThat(cart.getTotalPrice().getPrice()).isEqualByComparingTo("100.00");
    }

    @Test
    @DisplayName("Should throw IllegalCartException when removing an item key that doesn't map inside the list collection")
    void shouldThrowExceptionWhenRemovingMissingItemKey() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);

        assertThatThrownBy(() -> cart.removeProductFromCart(defaultCartItemId))
                .isInstanceOf(IllegalCartException.class)
                .hasMessage("Product not found in cart");
    }

    @Test
    @DisplayName("Should ensure structural domain equality (equals and hashCode) matches strictly based on CartId root field")
    void shouldRespectEqualsAndHashCodeContractBasedOnCartId() {
        Cart cart1 = new Cart(defaultCartId, defaultCustomerId);
        Cart cart2 = new Cart(defaultCartId, new CustomerId("different-customer"));
        Cart differentCart = new Cart(new CartId("cart-999"), defaultCustomerId);

        // Equals validations
        assertThat(cart1).isEqualTo(cart2);
        assertThat(cart1).isNotEqualTo(differentCart);
        assertThat(cart1).isNotEqualTo(null);

        // HashCode validations
        assertThat(cart1.hashCode()).isEqualTo(cart2.hashCode());
        assertThat(cart1.hashCode()).isNotEqualTo(differentCart.hashCode());
    }
}