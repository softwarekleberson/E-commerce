package com.cleancode.ecommerce.replacement.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.replacement.application.dto.ListReplacementOpenDto;

public interface ListReplacement {

	public Page<ListReplacementOpenDto> execute (Pageable pageable);
}
