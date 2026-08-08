package com.cleancode.ecommerce.adm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.adm.domain.Adm;
import com.cleancode.ecommerce.adm.domain.exception.IllegalAdmException;
import com.cleancode.ecommerce.promotional.domain.Voucher;
import com.cleancode.ecommerce.shared.kernel.Email;
import com.cleancode.ecommerce.shared.kernel.Password;
import com.cleancode.ecommerce.user.domain.UserId;

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
    @DisplayName("Should successfully assign a voucher and retrieve it by its unique identity key")
    void shouldAddAndGetVoucherById() {
        String voucherId = "VOUCH-99";
        when(voucherMock.getVoucherId()).thenReturn(voucherId);

        adm.addVoucher(voucherMock);
        Voucher retrievedVoucher = adm.getVoucherById(voucherId);

        assertThat(retrievedVoucher).isNotNull().isEqualTo(voucherMock);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    @DisplayName("Should throw exception when attempting to fetch a voucher using a null, empty, or blank identifier")
    void shouldThrowExceptionWhenGettingVoucherWithInvalidId(String invalidId) {
        assertThatThrownBy(() -> adm.getVoucherById(invalidId))
                .isInstanceOf(IllegalAdmException.class)
                .hasMessage("Id do voucher não pode ser nulo ou vazio");
    }

    @Test
    @DisplayName("Should successfully remove an existing voucher element from internal map")
    void shouldRemoveExistingVoucher() {
        String voucherId = "DISCOUNT-20";
        when(voucherMock.getVoucherId()).thenReturn(voucherId);
        adm.addVoucher(voucherMock);

        adm.removeVoucher(voucherId);

        assertThat(adm.getVoucherById(voucherId)).isNull();
        assertThat(adm.getAllVouchers()).isEmpty();
    }

    @Test
    @DisplayName("Should throw IllegalAdmException when attempting to delete a voucher identity that does not exist")
    void shouldThrowExceptionWhenRemovingNonExistentVoucher() {
        String nonexistentId = "MISSING-ID";

        assertThatThrownBy(() -> adm.removeVoucher(nonexistentId))
                .isInstanceOf(IllegalAdmException.class)
                .hasMessage("Voucher not found: " + nonexistentId);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    @DisplayName("Should throw exception when trying to remove a voucher using invalid id strings")
    void shouldThrowExceptionWhenRemovingVoucherWithInvalidId(String invalidId) {
        assertThatThrownBy(() -> adm.removeVoucher(invalidId))
                .isInstanceOf(IllegalAdmException.class)
                .hasMessage("Id do voucher não pode ser nulo ou vazio");
    }

    @Test
    @DisplayName("Should return an unmodifiable read-only wrapper containing all structural items via getAllVouchers")
    void shouldReturnImmutableCopyOfAllVouchers() {
        String voucherId = "SUMMER-SALE";
        when(voucherMock.getVoucherId()).thenReturn(voucherId);
        adm.addVoucher(voucherMock);

        Map<String, Voucher> allVouchers = adm.getAllVouchers();

        assertThat(allVouchers)
                .hasSize(1)
                .containsEntry(voucherId, voucherMock);

        // Ensures encapsulation contract is protected via Map.copyOf exception checking
        assertThatThrownBy(() -> allVouchers.put("NEW-ID", voucherMock))
                .isInstanceOf(UnsupportedOperationException.class);
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