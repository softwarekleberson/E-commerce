package com.cleancode.ecommerce.customer.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.address.Delivery;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

class DeliveryTest {

    @Test
    @DisplayName("Should successfully instantiate a delivery address if all data and the delivery phrase are valid")
    void shouldConstructDeliveryAddressSuccessfully() {
        Delivery deliveryAddress = new Delivery(
                "delivery-123", true, "Deixar na portaria", "John Doe", "Main St", "100", "Downtown", "12345678",
                "Apartment 4B", "Avenue", "Apartment", "New York", "NY", "USA"
        );

        assertThat(deliveryAddress.getPublicId()).isEqualTo("delivery-123");
        assertThat(deliveryAddress.isMain()).isTrue();
        assertThat(deliveryAddress.getDeliveryPhrase()).isEqualTo("Deixar na portaria");
        assertThat(deliveryAddress.getReceiver()).isEqualTo("John Doe");
        assertThat(deliveryAddress.getStreet()).isEqualTo("Main St");
        assertThat(deliveryAddress.getZipCode()).isEqualTo("12345678");
    }

    @Test
    @DisplayName("Should throw an exception if the delivery phrase (deliveryPhrase) is null or empty during construction")
    void shouldThrowExceptionWhenDeliveryPhraseIsMissing() {
        assertThatThrownBy(() -> new Delivery("id", true, null, "Rec", "St", "10", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Delivery Phrase is requerid");

        assertThatThrownBy(() -> new Delivery("id", true, "   ", "Rec", "St", "10", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Delivery Phrase is requerid");
    }

    @Test
    @DisplayName("Should update all delivery address properties, including the delivery phrase")
    void shouldUpdateAllPropertiesIncludingDeliveryPhrase() {
        Delivery deliveryAddress = new Delivery(
                "id", true, "Frase Antiga", "Rec", "St", "10", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country"
        );

        deliveryAddress.update(
                "Jane Doe", false, "Oak St", "20", "East", "invalid-zip", "Nova Obs", "Blvd", "House", "Boston", "MA", "Canada", "Nova Frase de Entrega"
        );

        assertThat(deliveryAddress.getReceiver()).isEqualTo("Jane Doe");
        assertThat(deliveryAddress.isMain()).isFalse();
        assertThat(deliveryAddress.getStreet()).isEqualTo("Oak St");
        assertThat(deliveryAddress.getZipCode()).isEqualTo("invalid-zip");
        assertThat(deliveryAddress.getObservation()).isEqualTo("Nova Obs");
        assertThat(deliveryAddress.getDeliveryPhrase()).isEqualTo("Nova Frase de Entrega");
    }

    @Test
    @DisplayName("Should not change current data if new update parameters are null or empty")
    void shouldNotOverwriteFieldsWithNullOrBlankOnUpdate() {
        Delivery deliveryAddress = new Delivery(
                "id", true, "Frase Original", "Rec Original", "St Original", "10", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country"
        );

        deliveryAddress.update(
                "", null, "   ", null, null, null, "", null, null, null, null, null, "   "
        );

        assertThat(deliveryAddress.getReceiver()).isEqualTo("Rec Original");
        assertThat(deliveryAddress.getStreet()).isEqualTo("St Original");
        assertThat(deliveryAddress.getDeliveryPhrase()).isEqualTo("Frase Original");
    }
}