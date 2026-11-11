package com.cleancode.ecommerce.cart.infra.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleancode.ecommerce.cart.infra.persistence.CartReservationSettingsEntity;

public interface CartReservationSettingsJpa extends JpaRepository<CartReservationSettingsEntity, Integer> {
}
