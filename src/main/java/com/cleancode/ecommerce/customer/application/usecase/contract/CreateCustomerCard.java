package com.cleancode.ecommerce.customer.application.usecase.contract;

import com.cleancode.ecommerce.customer.application.dtos.card.CreateCardDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;

public interface CreateCustomerCard {

	ListCustomerDto execute(String email, CreateCardDto dto);
}
