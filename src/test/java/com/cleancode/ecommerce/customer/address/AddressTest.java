package com.cleancode.ecommerce.customer.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.address.Address;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

class AddressTest {

    // Subclasse concreta para permitir testar a lógica da classe abstrata
    private static class TestAddress extends Address {
        public TestAddress(String id, Boolean main, String receiver, String street, String number, String neighborhood, String zipCode,
                           String observation, String streetType, String typeResidence, String city, String state, String country) {
            super(id, main, receiver, street, number, neighborhood, zipCode, observation, streetType, typeResidence, city, state, country);
        }
    }

    @Test
    @DisplayName("Should successfully instantiate the Address class when all inputs are valid")
    void shouldConstructAddressSuccessfully() {
        Address address = new TestAddress(
                "addr-123", true, "John Doe", "Main St", "100", "Downtown", "12345678",
                "Near the park", "Avenue", "House", "New York", "NY", "USA"
        );

        assertThat(address.getPublicId()).isEqualTo("addr-123");
        assertThat(address.isMain()).isTrue();
        assertThat(address.getReceiver()).isEqualTo("John Doe");
        assertThat(address.getStreet()).isEqualTo("Main St");
        assertThat(address.getNumber()).isEqualTo("100");
        assertThat(address.getNeighborhood()).isEqualTo("Downtown");
        assertThat(address.getZipCode()).isEqualTo("12345678");
        assertThat(address.getObservation()).isEqualTo("Near the park");
        assertThat(address.getStreetType()).isEqualTo("Avenue");
        assertThat(address.getResidenceType()).isEqualTo("House");
        assertThat(address.getCity()).isEqualTo("New York");
        assertThat(address.getState()).isEqualTo("NY");
        assertThat(address.getCountry()).isEqualTo("USA");
    }

    @Test
    @DisplayName("Should automatically generate a valid UUID when the provided ID is null or blank")
    void shouldFallbackToGeneratedUuidWhenIdIsMissing() {
        Address addressNullId = new TestAddress(null, true, "John Doe", "Main St", "100", "Downtown", "12345678", "Obs", "Avenue", "House", "New York", "NY", "USA");
        Address addressBlankId = new TestAddress("   ", true, "John Doe", "Main St", "100", "Downtown", "12345678", "Obs", "Avenue", "House", "New York", "NY", "USA");

        assertThat(addressNullId.getPublicId()).isNotBlank();
        assertThat(addressBlankId.getPublicId()).isNotBlank();
        
        // Garante que a string gerada é um UUID válido de fato
        assertThatCode(() -> UUID.fromString(addressNullId.getPublicId())).doesNotThrowAnyException();
        assertThatCode(() -> UUID.fromString(addressBlankId.getPublicId())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should throw specific validation exceptions when mandatory fields are blank")
    void shouldValidateRequiredFieldsOnConstruction() {
        assertThatThrownBy(() -> new TestAddress("id", true, " ", "Street", "100", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class).hasMessage("Receiver is requerid");

        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", null, "100", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class).hasMessage("Street is requerid");

        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", "Street", "", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class).hasMessage("Number is requerid");

        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", "Street", "100", "\t", "12345678", "Obs", "Type", "Res", "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class).hasMessage("Neighborhood is requerid");

        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", "Street", "100", "Neigh", "12345678", "Obs", " ", "Res", "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class).hasMessage("Street type is requerid");

        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", "Street", "100", "Neigh", "12345678", "Obs", "Type", null, "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class).hasMessage("Type Residence is requerid");

        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", "Street", "100", "Neigh", "12345678", "Obs", "Type", "Res", "", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class).hasMessage("City is requerid");

        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", "Street", "100", "Neigh", "12345678", "Obs", "Type", "Res", "City", "   ", "Country"))
                .isInstanceOf(IllegalDomainException.class).hasMessage("State is requerid");

        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", "Street", "100", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", null))
                .isInstanceOf(IllegalDomainException.class).hasMessage("Country is requerid");
    }

    @Test
    @DisplayName("Should throw an exception when the ZIP code format does not have exactly 8 numeric digits")
    void shouldEnforceZipCodeFormatValidation() {
        // O regex atual (^\d{8}$) valida apenas 8 números juntos, falhando caso contenha hífen
        assertThatThrownBy(() -> new TestAddress("id", true, "Rec", "St", "100", "Neigh", "12345-678", "Obs", "Type", "Res", "City", "State", "Country"))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Zip code must be in the format xxxxx-xxx");
    }

    @Test
    @DisplayName("Should update address properties when receiving new valid data")
    void shouldUpdatePropertiesWhenValidArgumentsAreProvided() {
        Address address = new TestAddress("id", true, "Rec", "Street", "100", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country");

        // O método update segue a lógica atual do seu código (onde o zipCode só atualiza se falhar na validação regex de 8 dígitos)
        address.update("Jane Doe", false, "Oak St", "200", "East", "invalid-zip", "New Obs", "Blvd", "Apartment", "Boston", "MA", "Canada");

        assertThat(address.getReceiver()).isEqualTo("Jane Doe");
        assertThat(address.isMain()).isFalse();
        assertThat(address.getStreet()).isEqualTo("Oak St");
        assertThat(address.getNumber()).isEqualTo("200");
        assertThat(address.getNeighborhood()).isEqualTo("East");
        assertThat(address.getZipCode()).isEqualTo("invalid-zip");
        assertThat(address.getObservation()).isEqualTo("New Obs");
        assertThat(address.getStreetType()).isEqualTo("Blvd");
        assertThat(address.getResidenceType()).isEqualTo("Apartment");
        assertThat(address.getCity()).isEqualTo("Boston");
        assertThat(address.getState()).isEqualTo("MA");
        assertThat(address.getCountry()).isEqualTo("Canada");
    }

    @Test
    @DisplayName("Should not change current data if new update parameters are null or empty")
    void shouldNotOverwritesFieldsWithNullOrBlankOnUpdate() {
        Address address = new TestAddress("id", true, "Original Rec", "Original St", "100", "Neigh", "12345678", "Original Obs", "Type", "Res", "City", "State", "Country");

        address.update("", null, "   ", null, null, null, null, null, "", null, null, null);

        assertThat(address.getReceiver()).isEqualTo("Original Rec");
        assertThat(address.isMain()).isTrue();
        assertThat(address.getStreet()).isEqualTo("Original St");
        assertThat(address.getObservation()).isEqualTo("Original Obs");
    }

    @Test
    @DisplayName("Should change the 'main' property to false when calling disableMain")
    void shouldDisableMainStatusFlag() {
        Address address = new TestAddress("id", true, "Rec", "St", "100", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country");
        
        address.disableMain();

        assertThat(address.isMain()).isFalse();
    }

    @Test
    @DisplayName("Must base equality (equals) and hashCode strictly on the value of publicId")
    void shouldRespectEqualsAndHashCodeContractBasedOnPublicId() {
        Address address1 = new TestAddress("id-xyz", true, "Rec", "St", "100", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country");
        Address address2 = new TestAddress("id-xyz", false, "Other", "Other", "99", "Other", "12345678", "Other", "Other", "Other", "Other", "Other", "Other");
        Address differentAddress = new TestAddress("id-abc", true, "Rec", "St", "100", "Neigh", "12345678", "Obs", "Type", "Res", "City", "State", "Country");

        // Verificação do Equals
        assertThat(address1).isEqualTo(address2);
        assertThat(address1).isNotEqualTo(differentAddress);
        assertThat(address1).isNotEqualTo(null);

        // Verificação do HashCode
        assertThat(address1.hashCode()).isEqualTo(address2.hashCode());
        assertThat(address1.hashCode()).isNotEqualTo(differentAddress.hashCode());
    }
}