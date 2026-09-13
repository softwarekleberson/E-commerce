package com.cleancode.ecommerce.replacement.application.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface ValueUnitProductService {

    public Optional<BigDecimal> findSubtotalByReservationId(String reservationId);

}
