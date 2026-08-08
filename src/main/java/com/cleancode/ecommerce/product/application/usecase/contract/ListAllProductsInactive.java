package com.cleancode.ecommerce.product.application.usecase.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;

public interface ListAllProductsInactive {

	Page<ListProductDto> execute(Pageable pageable);
}
