package com.cleancode.ecommerce.voucher.usecase.contract;

import java.math.BigDecimal;

public interface ReplacementVoucher {

	public void execute (String customerId, BigDecimal value);
}
