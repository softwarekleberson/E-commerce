package com.cleancode.ecommerce.shared.util.passwordencoder.contract;

public interface PasswordEncoderService {

	String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
