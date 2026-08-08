package com.cleancode.ecommerce.cart.application.dtos.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateCartDto(@NotBlank String reservationId ,@Min(0) int quantity) {

}
