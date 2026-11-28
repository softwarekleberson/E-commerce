package com.cleancode.ecommerce.voucher.usecase.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cleancode.ecommerce.voucher.domain.TypeVoucher;
import com.cleancode.ecommerce.voucher.domain.Voucher;

public record ListVoucherDto(String voucherId, String customerId, String message, LocalDate emission,
							 TypeVoucher typeVoucher, BigDecimal discount) {

	public ListVoucherDto(Voucher voucher) {
		this(voucher.getVoucherId(), voucher.getCustomerId().getValue(), voucher.getMessage().getMessage(), voucher.getEmission(), voucher.getTypeVoucher(), voucher.getDiscount().getDiscount());
	}

}
