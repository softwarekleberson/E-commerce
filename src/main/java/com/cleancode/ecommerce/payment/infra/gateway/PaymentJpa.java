package com.cleancode.ecommerce.payment.infra.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleancode.ecommerce.payment.infra.persistencia.PaymentEntity;

public interface PaymentJpa extends JpaRepository<PaymentEntity, String>{

}
