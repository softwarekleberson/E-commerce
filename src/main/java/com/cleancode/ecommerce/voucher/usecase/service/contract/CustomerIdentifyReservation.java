package com.cleancode.ecommerce.promotional.usecase.service.contract;

import com.cleancode.ecommerce.promotional.usecase.dto.ValueAndCustomerIdDto;

public interface CustomerIdentifyReservation {

	public ValueAndCustomerIdDto idCustomerInReservation(String reservationId);
}
