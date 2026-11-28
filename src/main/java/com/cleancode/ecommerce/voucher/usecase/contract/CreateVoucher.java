package com.cleancode.ecommerce.voucher.usecase.contract;

import com.cleancode.ecommerce.voucher.usecase.dto.CreateVoucherDto;

public interface CreateVoucher {

	public void execute (CreateVoucherDto dto);
}
