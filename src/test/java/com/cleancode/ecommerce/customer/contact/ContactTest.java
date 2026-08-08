package com.cleancode.ecommerce.customer.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.customer.domain.contact.Contact;
import com.cleancode.ecommerce.customer.domain.contact.Phone;
import com.cleancode.ecommerce.customer.domain.contact.TypePhone;
import com.cleancode.ecommerce.shared.kernel.Email;

@ExtendWith(MockitoExtension.class)
class ContactTest {

    @Mock
    private Phone phoneMock;

    @Mock
    private Email emailMock;

    @Test
    @DisplayName("Should correctly expose Phone and Email dependencies via getters")
    void shouldExposePhoneAndEmailCorrectly() {
        Contact contact = new Contact(phoneMock, emailMock);

        assertThat(contact.getFullPhone()).isEqualTo(phoneMock);
        assertThat(contact.getEmail()).isEqualTo(emailMock);
    }

    @Test
    @DisplayName("Should correctly delegate data calls to the internal Phone object")
    void shouldDelegateCallsToInternalPhoneInstance() {
        // Configurando o comportamento esperado do mock do telefone
        when(phoneMock.getDdd()).thenReturn("11");
        when(phoneMock.getPhone()).thenReturn("999998888");
        when(phoneMock.getTypePhone()).thenReturn(TypePhone.WHATSAPP);

        Contact contact = new Contact(phoneMock, emailMock);

        assertThat(contact.getDDD()).isEqualTo("11");
        assertThat(contact.getPhone()).isEqualTo("999998888");
        assertThat(contact.getTypePhone()).isEqualTo(TypePhone.WHATSAPP);
    }

    @Test
    @DisplayName("Must respect the equals and hashCode contract based on the internal Email and Phone objects")
    void shouldRespectEqualsAndHashCodeContract() {
        // Como o equals usa Objects.equals, usaremos instâncias reais simples ou mocks consistentes
        Contact contact1 = new Contact(phoneMock, emailMock);
        Contact contact2 = new Contact(phoneMock, emailMock);

        // Verificação de igualdade básica
        assertThat(contact1).isEqualTo(contact2);
        assertThat(contact1.hashCode()).isEqualTo(contact2.hashCode());

        // Verificação contra nulo e tipos diferentes
        assertThat(contact1).isNotEqualTo(null);
        assertThat(contact1).isNotEqualTo("uma string qualquer");
    }
}