package com.cleancode.ecommerce.payment.application.service.payment;

import java.math.BigDecimal;

import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.customer.domain.address.Delivery;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.event.order.EventTransportPublisher;
import com.cleancode.ecommerce.event.order.OrderEvent;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.dto.PaymentExecutionResult;
import com.cleancode.ecommerce.payment.application.service.payment.contract.Checkout;
import com.cleancode.ecommerce.payment.application.service.payment.contract.PaymentMethodFactory;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.Payment;
import com.cleancode.ecommerce.payment.domain.Total;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;
import com.cleancode.ecommerce.payment.domain.repository.PaymentRepository;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class CheckoutImpl implements Checkout {

	private final CustomerRepository customerRepository;
	private final CartRepository cartRepository;
	private final StockRepository stockRepository;
	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;
	private final PaymentMethodFactory paymentMethodFactory;
	private final EventTransportPublisher eventTransportPublisher;


	public CheckoutImpl(CustomerRepository customerRepository, CartRepository cartRepository,
			StockRepository stockRepository, PaymentRepository paymentRepository, OrderRepository orderRepository,
			PaymentMethodFactory paymentMethodFactory, EventTransportPublisher eventTransportPublisher) {

		this.customerRepository = customerRepository;
		this.cartRepository = cartRepository;
		this.stockRepository = stockRepository;
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
		this.paymentMethodFactory = paymentMethodFactory;
		this.eventTransportPublisher = eventTransportPublisher;
	}

	@Override
	public void execute(String email, PaymentDetails dto) {

		// 1️⃣ Buscar cliente e carrinho
		Customer customer = getCustomer(email);
		Cart cart = getCart(customer);

		// Encontra o endereço de entrega principal
		Delivery delivery = customer.findMainDelivery();
		String deliveryPublicId = delivery.getPublicId();
		
		
		// 2️⃣ Criar pedido e adicionar itens
		Order order = new Order(customer.getId().getValue(), deliveryPublicId);
		cart.getCartItens().forEach(item -> order.addItem(item.getProductName().getName(),
				item.getUnitPrice().getPrice(), item.getQuantity().getQuantity(), item.getReservationId()));

		// 3️⃣ Criar pagamento usando o total calculado pelo pedido
		Total orderTotal = order.calculateTotal();

		if (orderTotal.getTotalValue().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalDomainPayment("Order total must be greater than zero");
		}

		Payment payment = new Payment(customer.getId().getValue(), orderTotal, order.getOrderId().getOrderId());

		// 4️⃣ Adicionar descrição do pagamento (cartão, voucher)
		addDescriptionPayment(dto, payment);

		// 5️⃣ Processar pagamento via strategy
		PaymentMethod strategy = paymentMethodFactory.create(dto);
		PaymentExecutionResult result = payment.process(strategy, dto);

		// 6️⃣ Validar sucesso do pagamento
		if (!result.success()) {
			orderRepository.save(order);
			payment.rejectedPayment();
			paymentRepository.save(payment);
			throw new IllegalDomainPayment("Payment failed: " + result.errorMessage());
		}

		// 7️⃣ Marcar pedido como pago
		order.pay();
		payment.approvetPayment();

		// 8️⃣ Confirmar estoque
		confirmStock(cart);

		// 9️⃣ Persistir pedido e pagamento
		orderRepository.save(order);
		paymentRepository.save(payment);
		
		// 🔟 Limpar carrinho
		cart.removeAllProducts();
		cartRepository.save(cart);
		
		// Evento para atualizar todos os itens do pedido de eperando pagamento para em separação
		eventTransportPublisher.publish(new OrderEvent(order.getOrderId().getOrderId()));

	}

	private void addDescriptionPayment(PaymentDetails dto, Payment payment) {
		if (dto.numberCardOne() != null && dto.amountCardOne().compareTo(BigDecimal.ZERO) > 0) {
			payment.addDescriptionPayment(dto.typePayment());
		}
		if (dto.voucherId() != null) {
			payment.addDescriptionPayment(dto.typePayment());
		}
	}

	private void confirmStock(Cart cart) {
		cart.getCartItens().forEach(item -> {
			var productId = item.getProductId().getProductId();
			var reservationId = item.getReservationId();

			var stock = stockRepository.findStockByReservationId(reservationId)
					.orElseThrow(() -> new IllegalDomainPayment("Stock not found for reservation: " + reservationId));

			stock.confirmOrder(productId, reservationId);
			stockRepository.save(stock);
		});
	}

	private Cart getCart(Customer customer) {
		return cartRepository.getCartCustomer(customer.getId().getValue())
				.orElseThrow(() -> new IllegalDomainPayment("Cart not found for customer"));
	}

	private Customer getCustomer(String email) {
		return customerRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalDomainPayment("Customer not found with email: " + email));
	}
}