package com.cleancode.ecommerce.payment.infra.mapper;

import com.cleancode.ecommerce.payment.domain.DescriptionPayment;
import com.cleancode.ecommerce.payment.domain.TypePayment;
import com.cleancode.ecommerce.payment.infra.persistencia.DescriptionPaymentEntity;
import com.cleancode.ecommerce.payment.infra.persistencia.PaymentEntity;
import com.cleancode.ecommerce.payment.infra.persistencia.TypePaymentEntity;

public class DescriptionPaymentMapper {

	public static DescriptionPayment toDomain(DescriptionPaymentEntity entity) {
		return new DescriptionPayment(TypePayment.valueOf(entity.getType_payment().name()));
	}
	
	public static DescriptionPaymentEntity toEntity(DescriptionPayment domain, PaymentEntity paymentEntity) {
		DescriptionPaymentEntity entity = new DescriptionPaymentEntity();
		entity.setType_payment(TypePaymentEntity.valueOf(domain.getTypePayment().name()));
		entity.setPayment(paymentEntity);
		return entity;
	}
}
