package com.cleancode.ecommerce.customer.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.customer.domain.contact.Phone;
import com.cleancode.ecommerce.customer.domain.contact.TypePhone;
import com.cleancode.ecommerce.customer.domain.customer.exception.IllegalContactException;

class PhoneTest {

    @Test
    @DisplayName("Should successfully instantiate a landline with 8 digits")
    void shouldConstructLandlinePhoneSuccessfully() {
        Phone phone = new Phone("11", "33334444", TypePhone.LANDLINE);

        assertThat(phone.getDdd()).isEqualTo("11");
        assertThat(phone.getPhone()).isEqualTo("33334444");
        assertThat(phone.getTypePhone()).isEqualTo(TypePhone.LANDLINE);
    }

    @Test
    @DisplayName("Should successfully instantiate a mobile phone or WhatsApp number with 9 digits")
    void shouldConstructMobilePhoneSuccessfully() {
        Phone phone = new Phone("21", "999998888", TypePhone.MOBILE);

        assertThat(phone.getDdd()).isEqualTo("21");
        assertThat(phone.getPhone()).isEqualTo("999998888");
        assertThat(phone.getTypePhone()).isEqualTo(TypePhone.MOBILE);
    }

    @Test
    @DisplayName("Should throw exception when area code or phone number is null or empty")
    void shouldThrowExceptionWhenRequiredFieldsAreMissing() {
        assertThatThrownBy(() -> new Phone(null, "999998888", TypePhone.MOBILE))
                .isInstanceOf(IllegalContactException.class)
                .hasMessage("DDD cannot be null or empty.");

        assertThatThrownBy(() -> new Phone("  ", "999998888", TypePhone.MOBILE))
                .isInstanceOf(IllegalContactException.class)
                .hasMessage("DDD cannot be null or empty.");

        assertThatThrownBy(() -> new Phone("11", null, TypePhone.WHATSAPP))
                .isInstanceOf(IllegalContactException.class)
                .hasMessage("Phone cannot be null or empty.");

        assertThatThrownBy(() -> new Phone("11", "", TypePhone.WHATSAPP))
                .isInstanceOf(IllegalContactException.class)
                .hasMessage("Phone cannot be null or empty.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "111", "aa", "1a"})
    @DisplayName("Should throw an exception when the area code format does not have exactly 2 numeric digits")
    void shouldThrowExceptionWhenDddIsInvalid(String invalidDdd) {
        assertThatThrownBy(() -> new Phone(invalidDdd, "999998888", TypePhone.MOBILE))
                .isInstanceOf(IllegalContactException.class)
                .hasMessage("DDD must have exactly 2 digits.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567", "1234567890", "abcdefgh", "9999-8888"})
    @DisplayName("Should throw an exception when the phone format does not have between 8 and 9 numeric digits")
    void shouldThrowExceptionWhenPhoneIsInvalid(String invalidPhone) {
        assertThatThrownBy(() -> new Phone("11", invalidPhone, TypePhone.WHATSAPP))
                .isInstanceOf(IllegalContactException.class)
                .hasMessage("Phone must have 8 or 9 digits.");
    }

    @Test
    @DisplayName("Should ensure the equality contract (equals and hashCode) based on all attributes")
    void shouldRespectEqualsAndHashCodeContract() {
        Phone phone1 = new Phone("11", "999998888", TypePhone.MOBILE);
        Phone phone2 = new Phone("11", "999998888", TypePhone.MOBILE);
        Phone differentDdd = new Phone("21", "999998888", TypePhone.MOBILE);
        Phone differentNumber = new Phone("11", "888887777", TypePhone.MOBILE);
        Phone differentType = new Phone("11", "999998888", TypePhone.WHATSAPP);

        // Verificações do Equals
        assertThat(phone1).isEqualTo(phone2);
        assertThat(phone1).isNotEqualTo(differentDdd);
        assertThat(phone1).isNotEqualTo(differentNumber);
        assertThat(phone1).isNotEqualTo(differentType);
        assertThat(phone1).isNotEqualTo(null);

        // Verificações do HashCode
        assertThat(phone1.hashCode()).isEqualTo(phone2.hashCode());
        assertThat(phone1.hashCode()).isNotEqualTo(differentDdd.hashCode());
        assertThat(phone1.hashCode()).isNotEqualTo(differentType.hashCode());
    }
}