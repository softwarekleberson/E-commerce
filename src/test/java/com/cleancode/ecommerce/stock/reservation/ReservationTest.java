package com.cleancode.ecommerce.stock.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.cart.domain.CartId;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.stock.domain.reservation.Reservations;
import com.cleancode.ecommerce.stock.domain.reservation.ReserveStatus;

class ReservationTest {

    @Test
    @DisplayName("Should instantiate a reservation using the quick constructor with ACTIVE status and the current date")    void shouldConstructWithQuickConstructor() {
        LocalDateTime beforeCreation = LocalDateTime.now();
        
        Reservations reservation = new Reservations("cart-123", "customer-456", 5);
        
        LocalDateTime afterCreation = LocalDateTime.now();

        assertThat(reservation.getReservationId()).isNotNull();
        assertThat(reservation.getCartId()).isEqualTo(new CartId("cart-123"));
        assertThat(reservation.getCustomerId()).isEqualTo(new CustomerId("customer-456"));
        assertThat(reservation.getQuantity()).isEqualTo(5);
        assertThat(reservation.getReserveStatus()).isEqualTo(ReserveStatus.ACTIVE);
        
        // Garante que o timestamp foi gerado corretamente no momento da execução
        assertThat(reservation.getReservationTime()).isAfterOrEqualTo(beforeCreation);
        assertThat(reservation.getReservationTime()).isBeforeOrEqualTo(afterCreation);
    }

    @Test
    @DisplayName("Should instantiate a reservation using the full persistence constructor")    void shouldConstructWithFullConstructor() {
        LocalDateTime specificTime = LocalDateTime.of(2026, 7, 17, 12, 0);
        
        Reservations reservation = new Reservations(
            "res-777", 
            "cart-123", 
            "customer-456", 
            10, 
            specificTime, 
            ReserveStatus.ACTIVE
        );

        assertThat(reservation.getReservationId()).isEqualTo("res-777");
        assertThat(reservation.getCartId()).isEqualTo(new CartId("cart-123"));
        assertThat(reservation.getCustomerId()).isEqualTo(new CustomerId("customer-456"));
        assertThat(reservation.getQuantity()).isEqualTo(10);
        assertThat(reservation.getReservationTime()).isEqualTo(specificTime);
        assertThat(reservation.getReserveStatus()).isEqualTo(ReserveStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should change status to CANCELED when cancel method is triggered")    void shouldChangeStatusToCanceledWhenCanceled() {
        Reservations reservation = new Reservations("cart-123", "customer-456", 2);
        assertThat(reservation.getReserveStatus()).isEqualTo(ReserveStatus.ACTIVE);

        reservation.cancel();

        assertThat(reservation.getReserveStatus()).isEqualTo(ReserveStatus.CANCELED);
    }

    @Test
    @DisplayName("Should change the status to CONSUMED when the order is confirmed")    void shouldChangeStatusToConsumedWhenOrderIsConfirmed() {
        Reservations reservation = new Reservations("cart-123", "customer-456", 2);
        assertThat(reservation.getReserveStatus()).isEqualTo(ReserveStatus.ACTIVE);

        reservation.confirmOrder();

        assertThat(reservation.getReserveStatus()).isEqualTo(ReserveStatus.CONSUMED);
    }

    @Test
    @DisplayName("Should validate equality (equals and hashCode) based solely on the reservation ID")    void shouldRespectEqualsAndHashCodeContractBasedOnId() {
        LocalDateTime time = LocalDateTime.now();
        
        // Duas instâncias com o mesmo ID, mas dados completamente diferentes
        Reservations r1 = new Reservations("res-identical", "cart-1", "cust-1", 1, time, ReserveStatus.ACTIVE);
        Reservations r2 = new Reservations("res-identical", "cart-99", "cust-99", 99, time.plusDays(1), ReserveStatus.CANCELED);
        
        // Uma instância com ID diferente
        Reservations rDifferent = new Reservations("res-different", "cart-1", "cust-1", 1, time, ReserveStatus.ACTIVE);

        // Devem ser logicamente iguais por causa do ID
        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());

        // Comparações que devem falhar
        assertThat(r1).isNotEqualTo(rDifferent);
        assertThat(r1).isNotEqualTo(null);
        assertThat(r1).isNotEqualTo("res-identical"); // Tipo diferente
    }
}