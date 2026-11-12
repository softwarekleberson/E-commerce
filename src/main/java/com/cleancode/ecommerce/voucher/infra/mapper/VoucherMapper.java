package com.cleancode.ecommerce.promotional.infra.mapper;

import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.promotional.domain.Discount;
import com.cleancode.ecommerce.promotional.domain.Message;
import com.cleancode.ecommerce.promotional.domain.TypeVoucher;
import com.cleancode.ecommerce.promotional.domain.Voucher;
import com.cleancode.ecommerce.promotional.domain.VoucherId;
import com.cleancode.ecommerce.promotional.infra.persistence.TypeVoucherEntity;
import com.cleancode.ecommerce.promotional.infra.persistence.VoucherEntity;

public class VoucherMapper {

	public static VoucherEntity toEntity(Voucher voucher) {
		if (voucher == null)
			return null;

		VoucherEntity entity = new VoucherEntity();
		entity.setVoucherId(voucher.getVoucherId());
		entity.setCustomerId(voucher.getCustomerId().getValue()); 
		entity.setMessage(voucher.getMessage().getMessage()); 
		entity.setEmission(voucher.getEmission());
		entity.setTypeVoucher(toEntityType(voucher.getTypeVoucher()));
		entity.setDiscount(voucher.getDiscount().getDiscount());
		entity.setActive(voucher.isActive());
		return entity;
	}

	public static Voucher toDomain(VoucherEntity entity) {
		if (entity == null)
			return null;

		return new Voucher(new VoucherId(entity.getVoucherId()), new CustomerId(entity.getCustomerId()),
				new Message(entity.getMessage()), entity.getEmission(), toDomainType(entity.getTypeVoucher()),
				new Discount(entity.getDiscount()), entity.isActive());
	}

	private static TypeVoucherEntity toEntityType(TypeVoucher type) {
		if (type == null)
			return null;
		return TypeVoucherEntity.valueOf(type.name());
	}

	private static TypeVoucher toDomainType(TypeVoucherEntity type) {
		if (type == null)
			return null;
		return TypeVoucher.valueOf(type.name());
	}
}
