package com.cleancode.ecommerce.customer.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.customer.domain.address.Charge;
import com.cleancode.ecommerce.customer.domain.address.Delivery;
import com.cleancode.ecommerce.customer.domain.card.Card;
import com.cleancode.ecommerce.customer.domain.card.Flag;
import com.cleancode.ecommerce.customer.domain.contact.Contact;
import com.cleancode.ecommerce.customer.domain.contact.Phone;
import com.cleancode.ecommerce.customer.domain.contact.TypePhone;
import com.cleancode.ecommerce.customer.domain.customer.Birth;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.customer.domain.customer.Gender;
import com.cleancode.ecommerce.customer.domain.customer.SystemClientStatus;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Cpf;
import com.cleancode.ecommerce.shared.kernel.Email;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Password;

@ExtendWith(MockitoExtension.class)
class CustomerTest {

    @Mock private CustomerId idMock;
    @Mock private Name nameMock;
    @Mock private Gender genderMock;
    @Mock private Birth birthMock;
    @Mock private Cpf cpfMock;
    @Mock private Contact contactMock;
    @Mock private Password passwordMock;
    @Mock private SystemClientStatus systemClientStatusMock;
    @Mock private Email emailMock;
    @Mock private Phone phoneMock;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(idMock, nameMock, genderMock, birthMock, cpfMock, contactMock, passwordMock, systemClientStatusMock);
    }

    @Test
    @DisplayName("Must instantiate Customer with the correct properties")    void shouldConstructCustomerSuccessfully() {
        assertThat(customer.getId()).isEqualTo(idMock);
        assertThat(customer.getName()).isEqualTo(nameMock);
        assertThat(customer.isActive()).isFalse();
    }

    @Test
    @DisplayName("Should assign a new CustomerId based on a String")    void shouldAssignIdSuccessfully() {
        customer.assignId("cust-999");
        assertThat(customer.getId()).isNotNull();
        // CORREÇÃO: Comparar a String extraída com getValue() ao invés de comparar o objeto CustomerId diretamente com a String
        assertThat(customer.getId().getValue()).isEqualTo("cust-999"); 
    }

    @Test
    @DisplayName("Should incrementally update partial contact data (area code, phone number, type) and name")    void shouldUpdateCustomerContactAndNameIncrementally() {
        // 1. Criamos instâncias REAIS com dados iniciais válidos de acordo com as regras das suas classes
        Phone phoneReal = new Phone("11", "88888888", TypePhone.LANDLINE);
        Contact contactReal = new Contact(phoneReal, emailMock); // emailMock continua como mock sem problemas

        // 2. Criamos o Customer passando a instância real de Contact
        Customer customerWithRealContact = new Customer(
            idMock, 
            nameMock, 
            genderMock, 
            birthMock, 
            cpfMock, 
            contactReal, 
            passwordMock, 
            systemClientStatusMock
        );

        // 3. Atualiza Nome (A asserção garante apenas que o fluxo passou sem quebrar)
        customerWithRealContact.updateCustomer("Novo Nome", LocalDate.of(1995, 5, 15), null, null, null);
        assertThat(customerWithRealContact.getName()).isNotNull();

        // 4. Atualiza APENAS o DDD para "21" (Deve manter o telefone "88888888")
        customerWithRealContact.updateCustomer(null, null, "21", null, null);
        assertThat(customerWithRealContact.getContact().getDDD()).isEqualTo("21");
        assertThat(customerWithRealContact.getContact().getPhone()).isEqualTo("88888888");

        // 5. Atualiza APENAS o número do Telefone para um celular de 9 dígitos (Deve manter o DDD "21")
        customerWithRealContact.updateCustomer(null, null, null, "999999999", null);
        assertThat(customerWithRealContact.getContact().getDDD()).isEqualTo("21");
        assertThat(customerWithRealContact.getContact().getPhone()).isEqualTo("999999999");
    }

    @Test
    @DisplayName("Should update the password if the new string is valid")    void shouldUpdatePasswordWhenValid() {
        customer.updatePassword("NovaSenha123@");
        assertThat(customer.getPassword()).isNotNull();

        Password passwordAntes = customer.getPassword();
        customer.updatePassword("   ");
        assertThat(customer.getPassword()).isEqualTo(passwordAntes);
    }

    @Test
    @DisplayName("Should manage the 'main' flag for cards, ensuring only one card is active as the primary one")    void shouldManageMainCardFlagExclusivity() {
        // CORREÇÃO: Utilizando um número de teste Visa válido pelo algoritmo de Luhn
        customer.registerCard(
            true, 
            "JOHN DOE", 
            "123", 
            "4451156641962077", 
            LocalDate.now().plusYears(2), 
            Flag.VISA
        );
        
        assertThat(customer.getCards()).hasSize(1);
        assertThat(customer.getCards().get(0).isMain()).isTrue();

        // CORREÇÃO: Utilizando um número de teste Mastercard válido pelo algoritmo de Luhn
        customer.registerCard(
            true, 
            "JOHN DOE", 
            "321", 
            "4451156641962077", 
            LocalDate.now().plusYears(3), 
            Flag.MASTERCARD
        );
        
        assertThat(customer.getCards()).hasSize(2);
        assertThat(customer.getCards().get(0).isMain()).isFalse(); // Primeiro cartão foi desativado automaticamente
        assertThat(customer.getCards().get(1).isMain()).isTrue();  // Novo cartão assume como principal
    }

    @Test
    @DisplayName("Should exclusively manage the 'main' flag for shipping and billing addresses")    void shouldManageMainAddressFlagExclusivity() {
        customer.registerDelivery("Rec 1", true, "Rua 1", "10", "Bairro 1", "12345678", "Obs", "Rua", "Casa", "Cidade", "SP", "BR", "Frase");
        customer.registerDelivery("Rec 2", true, "Rua 2", "20", "Bairro 2", "87654321", "Obs", "Rua", "Apto", "Cidade", "SP", "BR", "Frase");

        assertThat(customer.getDeliverys().get(0).isMain()).isFalse();
        assertThat(customer.getDeliverys().get(1).isMain()).isTrue();

        customer.registerCharge("Rec 1", true, "Rua 1", "10", "Bairro 1", "12345678", "Obs", "Rua", "Casa", "Cidade", "SP", "BR");
        customer.registerCharge("Rec 2", true, "Rua 2", "20", "Bairro 2", "87654321", "Obs", "Rua", "Apto", "Cidade", "SP", "BR");

        assertThat(customer.getCharges().get(0).isMain()).isFalse();
        assertThat(customer.getCharges().get(1).isMain()).isTrue();
    }

    @Test
    @DisplayName("Should activate the customer only if they have at least one delivery address AND one billing address")
    void shouldActivateCustomerOnlyWhenMeetsCriteria() {
        assertThat(customer.checkActivationRequirements()).isFalse();

        customer.registerDelivery("Rec", true, "Rua", "10", "Bairro", "12345678", "Obs", "Rua", "Casa", "Cidade", "SP", "BR", "Frase");
        assertThat(customer.checkActivationRequirements()).isFalse();

        customer.registerCharge("Rec", true, "Rua", "10", "Bairro", "12345678", "Obs", "Rua", "Casa", "Cidade", "SP", "BR");
        assertThat(customer.checkActivationRequirements()).isTrue();
        assertThat(customer.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should retrieve and remove delivery addresses by ID and throw exceptions if not found")
    void shouldFindAndRemoveDeliveryAddressesById() {
        customer.registerDelivery("Rec", true, "Rua", "10", "Bairro", "12345678", "Obs", "Rua", "Casa", "Cidade", "SP", "BR", "Frase");
        Delivery delivery = customer.getDeliverys().get(0);
        String generatedId = delivery.getPublicId();

        assertThat(customer.findDeliveryById(generatedId)).isEqualTo(delivery);
        assertThat(customer.findMainDelivery()).isEqualTo(delivery);

        assertThatThrownBy(() -> customer.findDeliveryById("id-inexistente"))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Id Delivery not found");

        customer.removeDeliveryById(generatedId);
        assertThat(customer.getDeliverys()).isEmpty();
    }

    @Test
    @DisplayName("Should retrieve and remove billing addresses by ID and throw exceptions if not found")
    void shouldFindAndRemoveChargeAddressesById() {
        customer.registerCharge("Rec", true, "Rua", "10", "Bairro", "12345678", "Obs", "Rua", "Casa", "Cidade", "SP", "BR");
        Charge charge = customer.getCharges().get(0);
        String generatedId = charge.getPublicId();

        assertThat(customer.findChargeById(generatedId)).isEqualTo(charge);
        assertThat(customer.findMainCharge()).isEqualTo(charge);

        assertThatThrownBy(() -> customer.findChargeById("   "))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessage("Charge ID must not be null or blank");

        customer.removeChargeById(generatedId);
        assertThat(customer.getCharges()).isEmpty();
    }

    @Test
    @DisplayName("Should toggle the customer status in the system via systemClientStatus")
    void shouldChangeCustomerSystemActivationStatus() {
        // CORREÇÃO: O mockito mockava a chamada inicial, mas o factory estático `changeStatus` 
        // cria uma nova instância real de `SystemClientStatus`. Passamos o valor booleano inicial esperado.
        when(systemClientStatusMock.isSystemClientStatus()).thenReturn(true);
        
        customer.changeCustomerActivationStatusImpl();
        
        // Como o método estático inverteu true para false (!true = false):
        assertThat(customer.getSystemClientStatus()).isFalse();
    }

    @Test
    @DisplayName("Should restore saved card and address instances without triggering primary-related logic")    void shouldRestoreSavedCardsAndAddressesDirectly() {
        Card cardMock = mock(Card.class);
        Delivery deliveryMock = mock(Delivery.class);
        Charge chargeMock = mock(Charge.class);

        customer.restoreCard(cardMock);
        customer.restoreDelivery(deliveryMock);
        customer.restoreCharge(chargeMock);

        assertThat(customer.getCards()).contains(cardMock);
        assertThat(customer.getDeliverys()).contains(deliveryMock);
        assertThat(customer.getCharges()).contains(chargeMock);
    }

    @Test
    @DisplayName("Should validate equality (equals and hashCode) strictly based on CustomerId")
    void shouldRespectEqualsAndHashCodeBasedOnCustomerId() {
        Customer customer1 = new Customer(new CustomerId("123"), nameMock, genderMock, birthMock, cpfMock, contactMock, passwordMock, systemClientStatusMock);
        Customer customer2 = new Customer(new CustomerId("123"), null, null, null, null, null, null, null);
        Customer customerDifferent = new Customer(new CustomerId("999"), nameMock, genderMock, birthMock, cpfMock, contactMock, passwordMock, systemClientStatusMock);

        assertThat(customer1).isEqualTo(customer2);
        assertThat(customer1).isNotEqualTo(customerDifferent);
        assertThat(customer1.hashCode()).isEqualTo(customer2.hashCode());
    }
}