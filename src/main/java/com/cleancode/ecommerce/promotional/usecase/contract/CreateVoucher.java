package com.cleancode.ecommerce.promotional.usecase.contract;

import com.cleancode.ecommerce.promotional.usecase.dto.CreateVoucherDto;

public interface CreateVoucher {

	public void execute (CreateVoucherDto dto);
}
