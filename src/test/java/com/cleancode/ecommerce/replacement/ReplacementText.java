package com.cleancode.ecommerce.replacement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;

import com.cleancode.ecommerce.replacement.domain.Explain;
import com.cleancode.ecommerce.replacement.domain.Id;
import com.cleancode.ecommerce.replacement.domain.Reason;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.Status;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

class ReplacementText {

    private ReservationId reservationId;
    private Reason reason;
    private Explain explain;
    private Id id;

    @BeforeEach
    void setUp() {
        reservationId = Mockito.mock(ReservationId.class);
        reason = Mockito.mock(Reason.class);
        explain = Mockito.mock(Explain.class);
        id = Mockito.mock(Id.class);
    }

    @Nested
    @DisplayName("Creation of Replacement")
    class CreationTests {

        @Test
        @DisplayName("It must create a Replacement with OPEN status by default.")
        void shouldCreateReplacementWithOpenStatus() {
            Replacement replacement = new Replacement(reservationId, reason, explain);

            assertThat(replacement.getId()).isNotNull();
            assertThat(replacement.getReservationId()).isEqualTo(reservationId);
            assertThat(replacement.getReason()).isEqualTo(reason);
            assertThat(replacement.getExplain()).isEqualTo(explain);
            assertThat(replacement.getStatus()).isEqualTo(Status.OPEN);
        }

        @Test
        @DisplayName("You must create a Replacement with all the specified attributes.")
        void shouldCreateReplacementWithAllFields() {
            Replacement replacement = new Replacement(id, reservationId, reason, explain, Status.ACCEPTS);

            assertThat(replacement.getId()).isEqualTo(id);
            assertThat(replacement.getReservationId()).isEqualTo(reservationId);
            assertThat(replacement.getReason()).isEqualTo(reason);
            assertThat(replacement.getExplain()).isEqualTo(explain);
            assertThat(replacement.getStatus()).isEqualTo(Status.ACCEPTS);
        }
    }

    @Nested
    @DisplayName("State Transition")
    class StateTransitionTests {

        @Test
        @DisplayName("You must accept the request when the status is OPEN.")
        void shouldAcceptWhenStatusIsOpen() {
            Replacement replacement = new Replacement(id, reservationId, reason, explain, Status.OPEN);

            Replacement accepted = replacement.accept();

            assertThat(accepted.getStatus()).isEqualTo(Status.ACCEPTS);
            assertThat(accepted.getId()).isEqualTo(id);
            assertThat(accepted.getReservationId()).isEqualTo(reservationId);
        }

        @Test
        @DisplayName("You must deny the request when the status is OPEN.")
        void shouldNegateWhenStatusIsOpen() {
            Replacement replacement = new Replacement(id, reservationId, reason, explain, Status.OPEN);

            Replacement negated = replacement.negate();

            assertThat(negated.getStatus()).isEqualTo(Status.NEGATED);
            assertThat(negated.getId()).isEqualTo(id);
            assertThat(negated.getReservationId()).isEqualTo(reservationId);
        }

        @Test
        @DisplayName("You must cancel the request when the status is OPEN.")
        void shouldCancelWhenStatusIsOpen() {
            Replacement replacement = new Replacement(id, reservationId, reason, explain, Status.OPEN);

            Replacement cancelled = replacement.cancel();

            assertThat(cancelled.getStatus()).isEqualTo(Status.CLOSE);
            assertThat(cancelled.getId()).isEqualTo(id);
            assertThat(cancelled.getReservationId()).isEqualTo(reservationId);
        }

        @ParameterizedTest
        @EnumSource(value = Status.class, names = {"ACCEPTS", "NEGATED", "CLOSE"})
        @DisplayName("It should throw an exception when attempting to accept if the status is not OPEN.")
        void shouldThrowExceptionOnAcceptWhenNotOpen(Status status) {
            Replacement replacement = new Replacement(id, reservationId, reason, explain, status);

            assertThatThrownBy(replacement::accept)
                    .isInstanceOf(IllegalReplacementException.class)
                    .hasMessage("Only requests with the status OPEN can be modified.");
        }

        @ParameterizedTest
        @EnumSource(value = Status.class, names = {"ACCEPTS", "NEGATED", "CLOSE"})
        @DisplayName("It must throw an exception when attempting to deny if the status is not OPEN.")
        void shouldThrowExceptionOnNegateWhenNotOpen(Status status) {
            Replacement replacement = new Replacement(id, reservationId, reason, explain, status);

            assertThatThrownBy(replacement::negate)
                    .isInstanceOf(IllegalReplacementException.class)
                    .hasMessage("Only requests with the status OPEN can be modified.");
        }

        @ParameterizedTest
        @EnumSource(value = Status.class, names = {"ACCEPTS", "NEGATED", "CLOSE"})
        @DisplayName("It should throw an exception when attempting to cancel if the status is not OPEN.")
        void shouldThrowExceptionOnCancelWhenNotOpen(Status status) {
            Replacement replacement = new Replacement(id, reservationId, reason, explain, status);

            assertThatThrownBy(replacement::cancel)
                    .isInstanceOf(IllegalReplacementException.class)
                    .hasMessage("Only requests with the status OPEN can be modified.");
        }
    }

    @Nested
    @DisplayName("Equals, HashCode e ToString")
    class CommonMethodsTests {

        @Test
        @DisplayName("It must be equal to itself and to another object with the same ID.")
        void shouldBeEqualWithSameId() {
            Replacement replacement1 = new Replacement(id, reservationId, reason, explain, Status.OPEN);
            Replacement replacement2 = new Replacement(id, reservationId, reason, explain, Status.ACCEPTS);

            assertThat(replacement1).isEqualTo(replacement1);
            assertThat(replacement1).isEqualTo(replacement2);
            assertThat(replacement1.hashCode()).isEqualTo(replacement2.hashCode());
        }

        @Test
        @DisplayName("It must not be equal to an object with a different or null ID.")
        void shouldNotBeEqualWithDifferentId() {
            Id otherId = Mockito.mock(Id.class);
            Replacement replacement1 = new Replacement(id, reservationId, reason, explain, Status.OPEN);
            Replacement replacement2 = new Replacement(otherId, reservationId, reason, explain, Status.OPEN);

            assertThat(replacement1).isNotEqualTo(replacement2);
            assertThat(replacement1).isNotEqualTo(null);
            assertThat(replacement1).isNotEqualTo("outra String");
        }

        @Test
        @DisplayName("It should generate a toString method containing the main attributes.")
        void shouldGenerateProperToString() {
            Replacement replacement = new Replacement(id, reservationId, reason, explain, Status.OPEN);

            String result = replacement.toString();

            assertThat(result).contains("Replacement", "id=", "reservationId=", "reason=", "explain=", "status=OPEN");
        }
    }
}