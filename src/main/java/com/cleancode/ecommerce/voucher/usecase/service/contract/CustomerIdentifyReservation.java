package com.cleancode.ecommerce.voucher.usecase.service.contract;

import com.cleancode.ecommerce.voucher.usecase.dto.ValueAndCustomerIdDto;

public interface CustomerIdentifyReservation {

	public ValueAndCustomerIdDto idCustomerInReservation(String reservationId);
}
