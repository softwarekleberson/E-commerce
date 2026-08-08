package com.cleancode.ecommerce.promotional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.adm.domain.exception.IllegalAdmException;
import com.cleancode.ecommerce.promotional.domain.CodeVoucher;

class CodeVoucherTest {

    @Test
    @DisplayName("Should create a CodeVoucher successfully and convert text to uppercase")
    void shouldCreateCodeVoucherAndTransformToUpperCase() {
        CodeVoucher voucher = new CodeVoucher("promo2026");

        assertThat(voucher).isNotNull();
        // A regra de negócio garante que o código armazenado será sempre em CAIXA ALTA
        assertThat(voucher.getCode()).isEqualTo("PROMO2026");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", "\n", "\t"})
    @DisplayName("Should throw exception when provided code is null, empty, or blank")
    void shouldThrowExceptionWhenCodeIsInvalid(String invalidCode) {
        assertThatThrownBy(() -> new CodeVoucher(invalidCode))
                .isInstanceOf(IllegalAdmException.class)
                .hasMessageContaining("Code cannot be null or empty");
    }

    @Test
    @DisplayName("Should validate case-insensitive equality (equals and hashCode) due to internal conversion")
    void shouldRespectEqualsAndHashCodeContract() {
        CodeVoucher voucher1 = new CodeVoucher("blackfriday");
        CodeVoucher voucher2 = new CodeVoucher("BLACKFRIDAY");
        CodeVoucher voucherDifferent = new CodeVoucher("SUMMER20");

        // Como ambos são convertidos para "BLACKFRIDAY" internamente, eles devem ser idênticos
        assertThat(voucher1).isEqualTo(voucher2);
        assertThat(voucher1.hashCode()).isEqualTo(voucher2.hashCode());

        // Comparações com valores diferentes ou tipos incompatíveis
        assertThat(voucher1).isNotEqualTo(voucherDifferent);
        assertThat(voucher1).isNotEqualTo(null);
        assertThat(voucher1).isNotEqualTo("BLACKFRIDAY"); // String pura não é igual a CodeVoucher
    }
}