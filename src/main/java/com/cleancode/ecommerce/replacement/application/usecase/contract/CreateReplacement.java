package com.cleancode.ecommerce.replacement.application.usecase.contract;

import com.cleancode.ecommerce.replacement.application.dto.CreateReplacementDto;

public interface CreateReplacement {

	public void execute (CreateReplacementDto dto);
}
