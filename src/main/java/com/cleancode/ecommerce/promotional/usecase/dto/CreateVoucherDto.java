package com.cleancode.ecommerce.promotional.usecase.dto;

import java.math.BigDecimal;

import com.cleancode.ecommerce.promotional.domain.TypeVoucher;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateVoucherDto {
    
	@NotBlank(message = "The message content cannot be null or empty")
    private String message;
	
    @NotNull(message = "The Type voucher content cannot be null or empty")
    private TypeVoucher typeVoucher;
    
    @NotBlank(message = "The Id Customer content cannot be null or empty")
    private String customerId;
    
    @Min(value = 1, message = "Discount content cannot be null or empty")
    private BigDecimal discount;

	public CreateVoucherDto(String message, TypeVoucher typeVoucher, String customerId, BigDecimal discount) {
		this.message = message;
		this.typeVoucher = typeVoucher;
		this.customerId = customerId;
		this.discount = discount;
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public String getMessage() {
		return message;
	}

	public TypeVoucher getTypeVoucher() {
		return typeVoucher;
	}

	public BigDecimal getDiscount() {
		return discount;
	}
}