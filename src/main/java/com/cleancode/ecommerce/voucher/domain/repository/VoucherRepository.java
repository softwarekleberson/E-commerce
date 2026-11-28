package com.cleancode.ecommerce.voucher.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.voucher.domain.Voucher;

public interface VoucherRepository {

	void save (Voucher voucher);
	public Page<Voucher> listAllVouche(String id, Pageable pageable);
	Optional<Voucher> listSingleVoucher(String voucherId);
}
