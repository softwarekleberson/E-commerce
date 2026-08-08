package com.cleancode.ecommerce.customer.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.customer.SystemClientStatus;

class SystemClientStatusTest {

    @Test
    @DisplayName("Must instantiate SystemClientStatus with the given boolean state")    void shouldConstructSystemClientStatusWithGivenBoolean() {
        SystemClientStatus activeStatus = new SystemClientStatus(true);
        SystemClientStatus inactiveStatus = new SystemClientStatus(false);

        assertThat(activeStatus.isSystemClientStatus()).isTrue();
        assertThat(inactiveStatus.isSystemClientStatus()).isFalse();
    }

    @Test
    @DisplayName("Should toggle the current status when calling the static method changeStatus")    void shouldInvertStatusWhenChangeStatusIsCalled() {
        // Se passar 'true', deve retornar uma nova instância contendo 'false'
        SystemClientStatus fromTrue = SystemClientStatus.changeStatus(true);
        assertThat(fromTrue.isSystemClientStatus()).isFalse();

        // Se passar 'false', deve retornar uma nova instância contendo 'true'
        SystemClientStatus fromFalse = SystemClientStatus.changeStatus(false);
        assertThat(fromFalse.isSystemClientStatus()).isTrue();
    }

    @Test
    @DisplayName("Should ensure the equality contract (equals and hashCode) based on internal state")
    void shouldRespectEqualsAndHashCodeContract() {
        SystemClientStatus status1 = new SystemClientStatus(true);
        SystemClientStatus status2 = new SystemClientStatus(true);
        SystemClientStatus differentStatus = new SystemClientStatus(false);

        // Verificações do método equals
        assertThat(status1).isEqualTo(status2);
        assertThat(status1).isNotEqualTo(differentStatus);
        assertThat(status1).isNotEqualTo(null);
        assertThat(status1).isNotEqualTo("objeto de outro tipo");

        // Verificações do método hashCode
        assertThat(status1.hashCode()).isEqualTo(status2.hashCode());
        assertThat(status1.hashCode()).isNotEqualTo(differentStatus.hashCode());
    }
}