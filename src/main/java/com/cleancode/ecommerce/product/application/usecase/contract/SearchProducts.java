package com.cleancode.ecommerce.product.application.usecase.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.domain.ProductSearchCriteria;

public interface SearchProducts {
	Page<ListProductDto> execute(ProductSearchCriteria criteria, Pageable pageable);
}
