package com.cleancode.ecommerce.promotional.infra.gateway;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.cleancode.ecommerce.promotional.domain.Voucher;
import com.cleancode.ecommerce.promotional.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.promotional.infra.mapper.VoucherMapper;
import com.cleancode.ecommerce.promotional.infra.persistence.VoucherEntity;

@Repository
public class VoucherRepositoryJpa implements VoucherRepository {

	private final VoucherJpa jpa;

	public VoucherRepositoryJpa(VoucherJpa jpa) {
		this.jpa = jpa;
	}

	@Override
	public void save(Voucher voucher) {
		VoucherEntity entity = VoucherMapper.toEntity(voucher);
		jpa.save(entity);
	}

	@Override
	public Page<Voucher> listAllVouche(String id, Pageable pageable) {
	    Page<VoucherEntity> entities = jpa.findByCustomerIdAndActiveTrue(id, pageable);

	    return entities.map(VoucherMapper::toDomain);
	}

	@Override
	public Optional<Voucher> listSingleVoucher(String voucherId) {
	    return jpa.findByVoucherId(voucherId)
	              .map(VoucherMapper::toDomain);
	}
}
