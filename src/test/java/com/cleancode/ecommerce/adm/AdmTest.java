package com.cleancode.ecommerce.adm;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.adm.domain.Adm;
import com.cleancode.ecommerce.shared.kernel.domain.Email;
import com.cleancode.ecommerce.shared.kernel.domain.Password;
import com.cleancode.ecommerce.shared.kernel.domain.UserId;
import com.cleancode.ecommerce.voucher.domain.Voucher;

@ExtendWith(MockitoExtension.class)
class AdmTest {

    private Email validEmail;
    private Password validPassword;
    private Adm adm;

    @Mock
    private Voucher voucherMock;

    @BeforeEach
    void setUp() {
        validEmail = new Email("admin@cleancode.com");
        validPassword = new Password("AdminPass123!");
        adm = new Adm(validEmail, validPassword);
    }

    @Test
    @DisplayName("Should successfully call super constructors and inherit baseline identity state accurately")
    void shouldPassConstructorVariablesToSuperclass() {
        UserId specificUserId = new UserId();
        Adm targetedAdm = new Adm(specificUserId, validEmail, validPassword);

        assertThat(targetedAdm.getUserId()).isEqualTo(specificUserId.getUserId());
        assertThat(targetedAdm.getEmail()).isEqualTo(validEmail.getEmail());
    }
}