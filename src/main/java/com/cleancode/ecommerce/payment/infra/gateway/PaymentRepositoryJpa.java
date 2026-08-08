package com.cleancode.ecommerce.payment.infra.gateway;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cleancode.ecommerce.payment.domain.Payment;
import com.cleancode.ecommerce.payment.domain.repository.PaymentRepository;
import com.cleancode.ecommerce.payment.infra.mapper.PaymentMapper;
import com.cleancode.ecommerce.payment.infra.persistencia.PaymentEntity;

import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaymentRepositoryJpa implements PaymentRepository {

	private final PaymentJpa jpa;
	
	public PaymentRepositoryJpa(PaymentJpa jpa) {
		this.jpa = jpa;
	}
	
	@Override
	@Transactional
	public void save(Payment payment) {
		PaymentEntity entity = PaymentMapper.toEntity(payment);
		jpa.save(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Payment> getPaymentId(String paymentId) {
		return jpa.findById(paymentId).map(PaymentMapper::toDomain);
	}
}
