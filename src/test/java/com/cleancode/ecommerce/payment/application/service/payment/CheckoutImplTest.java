package com.cleancode.ecommerce.payment.application.service.payment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartItem;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.customer.domain.address.Delivery;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.event.order.EventTransportPublisher;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.dto.PaymentExecutionResult;
import com.cleancode.ecommerce.payment.application.service.payment.contract.PaymentMethodFactory;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.TypePayment;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;
import com.cleancode.ecommerce.payment.domain.repository.PaymentRepository;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.stock.application.dto.StockReservationConfirmation;
import com.cleancode.ecommerce.stock.application.usecase.contract.ConfirmStockReservations;
import com.cleancode.ecommerce.stock.domain.Quantity;

class CheckoutImplTest {

	private static final String CUSTOMER_EMAIL = "customer@example.test";
	private static final String CUSTOMER_ID = "customer-1";
	private static final BigDecimal PAYMENT_AMOUNT = new BigDecimal("120.00");

	private CustomerRepository customerRepository;
	private CartRepository cartRepository;
	private ConfirmStockReservations confirmStockReservations;
	private PaymentRepository paymentRepository;
	private OrderRepository orderRepository;
	private PaymentMethodFactory paymentMethodFactory;
	private PaymentMethod paymentMethod;
	private EventTransportPublisher eventPublisher;
	private CheckoutImpl checkout;
	private Cart cart;
	private PaymentDetails paymentDetails;

	@BeforeEach
	void setUp() {
		customerRepository = mock(CustomerRepository.class);
		cartRepository = mock(CartRepository.class);
		confirmStockReservations = mock(ConfirmStockReservations.class);
		paymentRepository = mock(PaymentRepository.class);
		orderRepository = mock(OrderRepository.class);
		paymentMethodFactory = mock(PaymentMethodFactory.class);
		paymentMethod = mock(PaymentMethod.class);
		eventPublisher = mock(EventTransportPublisher.class);
		checkout = new CheckoutImpl(customerRepository, cartRepository, confirmStockReservations, paymentRepository,
				orderRepository, paymentMethodFactory, eventPublisher);

		Customer customer = mock(Customer.class);
		when(customer.getId()).thenReturn(new CustomerId(CUSTOMER_ID));
		Delivery delivery = mock(Delivery.class);
		when(delivery.getPublicId()).thenReturn("delivery-1");
		when(customer.findMainDelivery()).thenReturn(delivery);
		when(customerRepository.findByEmail(CUSTOMER_EMAIL)).thenReturn(Optional.of(customer));

		cart = mock(Cart.class);
		when(cartRepository.getCartCustomer(CUSTOMER_ID)).thenReturn(Optional.of(cart));
		List<CartItem> cartItems = List.of(cartItem("product-1", "reservation-1"),
				cartItem("product-2", "reservation-2"));
		when(cart.getCartItens()).thenReturn(cartItems);

		paymentDetails = new PaymentDetails(TypePayment.CARD, "card-number", PAYMENT_AMOUNT, null, null, null);
		when(paymentMethodFactory.create(paymentDetails)).thenReturn(paymentMethod);
		when(paymentMethod.payment(PAYMENT_AMOUNT, paymentDetails))
				.thenReturn(new PaymentExecutionResult(true, "test-provider", null));
	}

	@Test
	void checkoutConfirmsAllCartReservationsTogetherBeforePersistingOrder() {
		checkout.execute(CUSTOMER_EMAIL, paymentDetails);

		verify(confirmStockReservations).execute(List.of(
				new StockReservationConfirmation("reservation-1", "product-1"),
				new StockReservationConfirmation("reservation-2", "product-2")));
		verify(orderRepository).save(org.mockito.ArgumentMatchers.any());
		verify(paymentRepository).save(org.mockito.ArgumentMatchers.any());
		verify(cartRepository).save(cart);
		verify(eventPublisher).publish(org.mockito.ArgumentMatchers.any());
	}

	@Test
	void stockConfirmationFailureStopsOrderAndCartPersistence() {
		doThrow(new IllegalDomainPayment("Stock confirmation failed"))
				.when(confirmStockReservations).execute(anyCollection());

		assertThrows(IllegalDomainPayment.class, () -> checkout.execute(CUSTOMER_EMAIL, paymentDetails));

		verify(orderRepository, never()).save(org.mockito.ArgumentMatchers.any());
		verify(paymentRepository, never()).save(org.mockito.ArgumentMatchers.any());
		verify(cartRepository, never()).save(cart);
		verify(eventPublisher, never()).publish(org.mockito.ArgumentMatchers.any());
	}

	private CartItem cartItem(String productId, String reservationId) {
		CartItem item = mock(CartItem.class);
		when(item.getProductName()).thenReturn(new Name("Product " + productId));
		when(item.getProductId()).thenReturn(new ProductId(productId));
		when(item.getUnitPrice()).thenReturn(new Price(new BigDecimal("60.00"), TypeCoin.DOLAR));
		when(item.getQuantity()).thenReturn(new Quantity(1));
		when(item.getReservationId()).thenReturn(reservationId);
		return item;
	}
}
