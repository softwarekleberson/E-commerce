package com.cleancode.ecommerce.payment.infra.mapper;

import com.cleancode.ecommerce.cart.infra.persistence.TypeCoinEntity;
import com.cleancode.ecommerce.payment.domain.Payment;
import com.cleancode.ecommerce.payment.domain.StatusPayment;
import com.cleancode.ecommerce.payment.infra.persistencia.PaymentEntity;
import com.cleancode.ecommerce.payment.infra.persistencia.StatusPaymentEntity;

public class PaymentMapper {

	public static Payment toDomain(PaymentEntity entity) {

		StatusPayment status = StatusPayment.valueOf(entity.getStatus_Payment().name());

		Payment payment = new Payment(entity.getCustomer_id(), entity.getPayment_id(), entity.getPayment_date(),
				status, entity.getOrder_id());

		entity.getDescription_payment().stream().map(DescriptionPaymentMapper::toDomain)
				.forEach(desc -> payment.addDescriptionPayment(desc.getTypePayment()));

		return payment;
	}

	public static PaymentEntity toEntity(Payment payment) {

		PaymentEntity entity = new PaymentEntity();

		entity.setPayment_id(payment.getPaymentId().getId());
		entity.setCustomer_id(payment.getCustomerId().getValue());
		entity.setPayment_date(payment.getPaymentDate());
		entity.setTotal(payment.getTotal().getTotalValue());
		entity.setType_coin(TypeCoinEntity.valueOf(payment.getTotal().getTypeCoin().name()));
		entity.setStatus_Payment(StatusPaymentEntity.valueOf(payment.getStatusPayment().name()));
		entity.setOrder_id(payment.getOrderId().getOrderId());

		entity.setDescription_payment(payment.getDescription().stream()
				.map(desc -> DescriptionPaymentMapper.toEntity(desc, entity)).toList());

		return entity;
	}
}
