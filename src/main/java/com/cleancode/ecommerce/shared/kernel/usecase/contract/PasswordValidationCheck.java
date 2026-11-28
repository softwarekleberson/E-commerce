package com.cleancode.ecommerce.shared.kernel.usecase.contract;

public interface PasswordValidationCheck {

	public void validateAcceptablePasswordFormat(String password);
	public void passwordCheckAndConfirmPassword(String password, String confirmPassword);
}
