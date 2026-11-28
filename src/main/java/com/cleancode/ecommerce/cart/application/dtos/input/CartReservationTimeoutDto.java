package com.cleancode.ecommerce.cart.application.dtos.input;

import jakarta.validation.constraints.Positive;

public record CartReservationTimeoutDto(@Positive int minutes) {
}
