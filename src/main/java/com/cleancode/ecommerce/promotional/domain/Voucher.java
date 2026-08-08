package com.cleancode.ecommerce.promotional.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.cleancode.ecommerce.customer.domain.customer.CustomerId;

public class Voucher {

	private final VoucherId voucherId;
	private final CustomerId customerId;
	private final Message message;
	private final LocalDate emission;
	private final TypeVoucher typeVoucher;
	private final Discount discount;
	private boolean active;

	public Voucher(VoucherId voucherId, CustomerId customerId, Message message, LocalDate emission,
			TypeVoucher typeVoucher, Discount discount, boolean active) {

		this.voucherId = voucherId;
		this.customerId = customerId;
		this.message = message;
		this.emission = emission;
		this.typeVoucher = typeVoucher;
		this.discount = discount;
		this.active = active;
	}
	
	public Voucher(String customerId, String message, TypeVoucher typeVoucher, BigDecimal discount) {
		this.voucherId = new VoucherId();
		this.customerId = new CustomerId(customerId);
		this.message = new Message(message);
		this.emission = LocalDate.now();
		this.typeVoucher = typeVoucher;
		this.discount = new Discount(discount);
	}

	public boolean apply(BigDecimal totalAmount) {
		if(!isActive()) {
			return false;
		}
		
		if(this.discount.getDiscount().compareTo(totalAmount) >= 0) {
			deactivateVoucher();
			return true;
		}
		return false;
	}
	
	public Discount discountAfterUse(BigDecimal totalAmount) {
		if(this.discount.getDiscount().compareTo(totalAmount) > 0) {
			BigDecimal discountAfterPayment = this.discount.getDiscount().subtract(totalAmount);
			Discount discount = new Discount(discountAfterPayment);
			return discount;
		}
		
		return new Discount(BigDecimal.ZERO);
	}
	
	public void disableVoucherAfterUse() {
		if(isActive()) {
			deactivateVoucher();
		}
	}
		
	public boolean isActive() {
		return active;
	}
	
	private boolean deactivateVoucher() {
		return this.active = false;
	}

	public String getVoucherId() {
		return voucherId.getVoucherId();
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public Message getMessage() {
		return message;
	}

	public LocalDate getEmission() {
		return emission;
	}

	public TypeVoucher getTypeVoucher() {
		return typeVoucher;
	}

	public Discount getDiscount() {
		return discount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(voucherId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voucher other = (Voucher) obj;
		return Objects.equals(voucherId, other.voucherId);
	}
}
