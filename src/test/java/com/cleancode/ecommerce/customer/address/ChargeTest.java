package com.cleancode.ecommerce.customer.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.address.Charge;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

class ChargeTest {

    @Test
    @DisplayName("Should successfully instantiate a billing address (Charge) if all data is valid")
    void shouldConstructChargeAddressSuccessfully() {
        Charge chargeAddress = new Charge(
                "charge-id-123", false, "Empresa XPTO", "Av Paulista", "1000", "Bela Vista", "01311000",
                "Conjunto 42", "Avenida", "Comercial", "São Paulo", "SP", "Brasil"
        );

        assertThat(chargeAddress.getPublicId()).isEqualTo("charge-id-123");
        assertThat(chargeAddress.isMain()).isFalse();
        assertThat(chargeAddress.getReceiver()).isEqualTo("Empresa XPTO");
        assertThat(chargeAddress.getStreet()).isEqualTo("Av Paulista");
        assertThat(chargeAddress.getNumber()).isEqualTo("1000");
        assertThat(chargeAddress.getNeighborhood()).isEqualTo("Bela Vista");
        assertThat(chargeAddress.getZipCode()).isEqualTo("01311000");
        assertThat(chargeAddress.getObservation()).isEqualTo("Conjunto 42");
        assertThat(chargeAddress.getStreetType()).isEqualTo("Avenida");
        assertThat(chargeAddress.getResidenceType()).isEqualTo("Comercial");
        assertThat(chargeAddress.getCity()).isEqualTo("São Paulo");
        assertThat(chargeAddress.getState()).isEqualTo("SP");
        assertThat(chargeAddress.getCountry()).isEqualTo("Brasil");
    }

    @Test
    @DisplayName("Should trigger validations inherited from Address when there are invalid fields")
    void shouldTriggerInheritedValidationsOnChargeConstruction() {
        // Validação de campo obrigatório ausente (Receiver)
        assertThatThrownBy(() -> new Charge("id", true, "", "Rua A", "10", "Centro", "12345678", "Obs", "Rua", "Casa", "Cidade", "ST", "País"))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Receiver is requerid");

        // Validação de formato do CEP inválido
        assertThatThrownBy(() -> new Charge("id", true, "Recebedor", "Rua A", "10", "Centro", "12345-678", "Obs", "Rua", "Casa", "Cidade", "ST", "País"))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Zip code must be in the format xxxxx-xxx");
    }
}