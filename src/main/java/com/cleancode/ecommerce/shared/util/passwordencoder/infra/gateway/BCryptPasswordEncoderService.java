package com.cleancode.ecommerce.shared.util.passwordencoder.infra.gateway;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cleancode.ecommerce.shared.util.passwordencoder.contract.PasswordEncoderService;

@Service
public class BCryptPasswordEncoderService implements PasswordEncoderService {

	private final BCryptPasswordEncoder encoder;

    public BCryptPasswordEncoderService(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}