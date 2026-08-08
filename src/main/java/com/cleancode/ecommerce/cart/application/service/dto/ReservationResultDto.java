package com.cleancode.ecommerce.cart.application.service.dto;

import com.cleancode.ecommerce.stock.domain.Stock;

public record ReservationResultDto(Stock stock, String reservationId) {

}
