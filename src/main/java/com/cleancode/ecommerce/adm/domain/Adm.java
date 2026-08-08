package com.cleancode.ecommerce.adm.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.cleancode.ecommerce.adm.domain.exception.IllegalAdmException;
import com.cleancode.ecommerce.promotional.domain.Voucher;
import com.cleancode.ecommerce.shared.kernel.Email;
import com.cleancode.ecommerce.shared.kernel.Password;
import com.cleancode.ecommerce.user.domain.User;
import com.cleancode.ecommerce.user.domain.UserId;

public class Adm extends User {

	private Map<String, Voucher> vouchers = new HashMap<>();

	public Adm(Email email, Password password) {
		super(Objects.requireNonNull(email), Objects.requireNonNull(password));
	}

	public Adm(UserId userId, Email email, Password password) {
		super(userId, email, password);
	}
	
	public void addVoucher(Voucher voucher) {
		this.vouchers.put(voucher.getVoucherId(), voucher);
	}

	public Voucher getVoucherById(String id) {
		validateId(id);
		return this.vouchers.get(id);
	}

	public void removeVoucher(String id) {
		validateId(id);

		if (vouchers.remove(id) == null) {
			throw new IllegalAdmException("Voucher not found: " + id);
		}
	}

	private void validateId(String id) {
		if (id == null || id.isBlank()) {
			throw new IllegalAdmException("Id do voucher não pode ser nulo ou vazio");
		}
	}

	public Map<String, Voucher> getAllVouchers() {
		return Map.copyOf(vouchers);
	}
}
