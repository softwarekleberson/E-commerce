package com.cleancode.ecommerce.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

class ReservationIdTest {

    @Test
    @DisplayName("Should create a ReservationId with the exact provided string value")
    void shouldCreateWithProvidedId() {
        String customId = "res-4567";

        ReservationId reservationId = new ReservationId(customId);

        assertThat(reservationId.getReservationId()).isEqualTo(customId);
    }

    @Test
    @DisplayName("Should generate a valid random UUID when using the default constructor")
    void shouldGenerateUuidAutomatically() {
        ReservationId reservationId = new ReservationId();

        assertThat(reservationId.getReservationId()).isNotNull();
        // Structural validation to verify the generated string is a true UUID format
        assertThatCode(() -> UUID.fromString(reservationId.getReservationId())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should allow and retain a null value when explicit string constructor receives null")
    void shouldAllowNullValueWithoutThrowingException() {
        ReservationId reservationId = new ReservationId(null);

        assertThat(reservationId.getReservationId()).isNull();
    }

    @Test
    @DisplayName("Should ensure identity equality (equals and hashCode) matches for identical reservation values")
    void shouldRespectEqualsAndHashCodeContract() {
        String commonId = "reservation-123";
        ReservationId id1 = new ReservationId(commonId);
        ReservationId id2 = new ReservationId(commonId);
        ReservationId differentId = new ReservationId("reservation-789");

        // Equals validations
        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(differentId);
        assertThat(id1).isNotEqualTo(null);

        // HashCode validations
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
        assertThat(id1.hashCode()).isNotEqualTo(differentId.hashCode());
    }
}