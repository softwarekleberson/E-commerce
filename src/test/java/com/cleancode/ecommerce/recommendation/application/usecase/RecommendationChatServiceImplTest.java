package com.cleancode.ecommerce.recommendation.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.order.domain.state.order.OrderStatus;
import com.cleancode.ecommerce.order.domain.state.order.PaidState;
import com.cleancode.ecommerce.order.domain.state.order.PendingState;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.recommendation.infra.client.RagRecommendationClient;
import com.cleancode.ecommerce.recommendation.infra.gateway.RecommendationChatFeedbackJpa;
import com.cleancode.ecommerce.recommendation.infra.persistence.RecommendationChatFeedbackEntity;
import com.cleancode.ecommerce.shared.kernel.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

@ExtendWith(MockitoExtension.class)
class RecommendationChatServiceImplTest {

	@Mock
	private RagRecommendationClient ragClient;
	@Mock
	private CustomerIdentityIdService customerIdentityIdService;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private StockRepository stockRepository;
	@Mock
	private RecommendationChatFeedbackJpa feedbackRepository;

	private RecommendationChatServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new RecommendationChatServiceImpl(ragClient, customerIdentityIdService, orderRepository,
				productRepository, stockRepository, feedbackRepository);
	}

	@Test
	void returnsAnIdentifierAndStoresTheAuthenticatedUserForEachAnswer() {
		when(customerIdentityIdService.customerIdentityIdService("customer@example.test"))
				.thenReturn(Optional.empty());
		when(ragClient.recommend("Quero um livro", List.of()))
				.thenReturn(new RagRecommendationClient.RagAnswer("Resposta", List.of()));
		when(stockRepository.findAll()).thenReturn(List.of());

		var response = service.recommend("Quero um livro", "customer@example.test");

		ArgumentCaptor<RecommendationChatFeedbackEntity> interactionCaptor = ArgumentCaptor
				.forClass(RecommendationChatFeedbackEntity.class);
		verify(feedbackRepository).save(interactionCaptor.capture());
		assertThat(response.responseId()).isEqualTo(interactionCaptor.getValue().getResponseId());
		assertThat(response.answer()).isEqualTo("Resposta");
		assertThat(response.liked()).isNull();
		assertThat(interactionCaptor.getValue().getUserEmail()).isEqualTo("customer@example.test");
	}

	@Test
	void sendsOnlyPaidOrderCategoryPreferencesToTheRagService() {
		when(customerIdentityIdService.customerIdentityIdService("customer@example.test"))
				.thenReturn(Optional.of("customer-1"));
		when(orderRepository.getOrdersByCustomer("customer-1", Pageable.ofSize(100)))
				.thenReturn(new PageImpl<>(List.of(
						createOrder("paid-order", OrderStatus.PAID, new PaidState(), "book-1", 2),
						createOrder("pending-order", OrderStatus.PENDING, new PendingState(), "toy-1", 8))));
		Product purchasedBook = org.mockito.Mockito.mock(Product.class);
		when(purchasedBook.getProductCategory()).thenReturn(ProductCategory.BOOKS);
		when(productRepository.findById("book-1")).thenReturn(Optional.of(purchasedBook));
		when(ragClient.recommend("Quero uma leitura", List.of(
				new RagRecommendationClient.PurchasePreference("BOOKS", 2))))
				.thenReturn(new RagRecommendationClient.RagAnswer("Resposta", List.of()));
		when(stockRepository.findAll()).thenReturn(List.of());

		service.recommend("Quero uma leitura", "customer@example.test");

		verify(productRepository).findById("book-1");
		verify(productRepository, never()).findById("toy-1");
		verify(ragClient).recommend("Quero uma leitura",
				List.of(new RagRecommendationClient.PurchasePreference("BOOKS", 2)));
	}

	@Test
	void storesTheFeedbackForAnAnswerOwnedByTheAuthenticatedUser() {
		var interaction = new RecommendationChatFeedbackEntity("response-1", "customer@example.test", null, null, null);
		when(feedbackRepository.findByResponseIdAndUserEmail("response-1", "customer@example.test"))
				.thenReturn(Optional.of(interaction));

		var response = service.submitFeedback("response-1", "customer@example.test", true);

		assertThat(response.responseId()).isEqualTo("response-1");
		assertThat(response.liked()).isTrue();
		assertThat(interaction.getLiked()).isTrue();
		assertThat(interaction.getFeedbackAt()).isNotNull();
		verify(feedbackRepository).save(interaction);
	}

	@Test
	void doesNotAllowAUserToRateAnotherUsersAnswer() {
		when(feedbackRepository.findByResponseIdAndUserEmail("response-1", "other@example.test"))
				.thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.submitFeedback("response-1", "other@example.test", false))
				.isInstanceOf(ResponseStatusException.class)
				.satisfies(exception -> assertThat(((ResponseStatusException) exception).getStatusCode())
						.isEqualTo(HttpStatus.NOT_FOUND));

		verify(feedbackRepository, never()).save(any(RecommendationChatFeedbackEntity.class));
	}

	private Order createOrder(String orderId, OrderStatus status,
			com.cleancode.ecommerce.order.domain.state.order.OrderState state, String productId, int quantity) {
		Order order = new Order(orderId, "customer-1", "delivery-1", LocalDateTime.now(), status, state, false);
		order.addItem("Purchased product", productId, new BigDecimal("10.00"), quantity, "reservation-" + orderId);
		return order;
	}
}
