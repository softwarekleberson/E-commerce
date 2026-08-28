package com.cleancode.ecommerce.payment.infra.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.event.order.EventTransportPublisher;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.payment.application.service.payment.CheckoutImpl;
import com.cleancode.ecommerce.payment.application.service.payment.PaymentMethodFactoryImpl;
import com.cleancode.ecommerce.payment.application.service.payment.contract.Checkout;
import com.cleancode.ecommerce.payment.application.service.payment.contract.PaymentGatewayClient;
import com.cleancode.ecommerce.payment.application.service.payment.contract.PaymentMethodFactory;
import com.cleancode.ecommerce.payment.application.service.voucher.VoucherAfterUseImpl;
import com.cleancode.ecommerce.payment.application.service.voucher.VoucherPaymentServiceImpl;
import com.cleancode.ecommerce.payment.application.service.voucher.contract.VoucherAfterUseService;
import com.cleancode.ecommerce.payment.application.service.voucher.contract.VoucherPaymentService;
import com.cleancode.ecommerce.payment.application.usecase.CardAndCouponPayment;
import com.cleancode.ecommerce.payment.application.usecase.CardPayment;
import com.cleancode.ecommerce.payment.application.usecase.TwoCardsPayment;
import com.cleancode.ecommerce.payment.application.usecase.VoucherPayment;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.repository.PaymentRepository;
import com.cleancode.ecommerce.promotional.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

@Configuration
public class PaymentConfig {

	@Bean
	public Checkout checkout(CustomerRepository customerRepository, CartRepository cartRepository,
			StockRepository stockRepository, PaymentRepository paymentRepository,OrderRepository orderRepository,
			PaymentMethodFactory paymentMethodFactory, EventTransportPublisher eventTransportPublisher) {
		return new CheckoutImpl(customerRepository, cartRepository, stockRepository, paymentRepository,
				orderRepository ,paymentMethodFactory, eventTransportPublisher);
	}
	
	@Bean
    public PaymentMethodFactory paymentMethodFactory(List<PaymentMethod> methods) {
        return new PaymentMethodFactoryImpl(methods);
    }
	
    @Bean
    public PaymentMethod cardAndCouponPayment(
            PaymentGatewayClient gateway,
    		VoucherPaymentService service
    ) {
        return new CardAndCouponPayment(service, gateway);
    }
    
    @Bean
    public PaymentMethod cardPayment(PaymentGatewayClient gateway) {
        return new CardPayment(gateway);
    }
    
    @Bean
    public PaymentMethod twocardsPayment(PaymentGatewayClient gateway) {
        return new TwoCardsPayment(gateway);
    }
    
    @Bean
    public PaymentMethod voucherPayment(VoucherPaymentService service) {
        return new VoucherPayment(service);
    }
    
    @Bean
    public VoucherPaymentService voucherPaymentService(	VoucherRepository repository, VoucherAfterUseService voucherAfterUseService) {
    	return new VoucherPaymentServiceImpl(repository, voucherAfterUseService);
    }
    
    @Bean
    public VoucherAfterUseService voucherAfterUseService (VoucherRepository repository) {
    	return new VoucherAfterUseImpl(repository);
    }
}
