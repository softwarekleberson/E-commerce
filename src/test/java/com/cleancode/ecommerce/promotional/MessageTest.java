package com.cleancode.ecommerce.promotional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.promotional.domain.Message;

class MessageTest {

    @Test
    @DisplayName("Should create Message successfully when text is valid")
    void shouldCreateMessageSuccessfully() {
        String text = "Cupom de 10% de desconto aplicado com sucesso!";
        Message message = new Message(text);

        assertThat(message).isNotNull();
        assertThat(message.getMessage()).isEqualTo(text);
    }

    @Test
    @DisplayName("Should allow empty or blank messages as class does not prevent them")
    void shouldAllowEmptyOrBlankMessages() {
        Message emptyMessage = new Message("");
        Message blankMessage = new Message("   ");

        assertThat(emptyMessage.getMessage()).isEmpty();
        assertThat(blankMessage.getMessage()).isEqualTo("   ");
    }

    @Test
    @DisplayName("Should throw NullPointerException when message string is null")
    void shouldThrowNullPointerExceptionWhenMessageIsNull() {
        assertThatThrownBy(() -> new Message(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should strictly validate equals and hashCode contract")
    void shouldRespectEqualsAndHashCodeContract() {
        Message msgA1 = new Message("Bem-vindo ao E-commerce");
        Message msgA2 = new Message("Bem-vindo ao E-commerce");
        Message msgB = new Message("Outra mensagem qualquer");

        // Conteúdos idênticos geram objetos logicamente iguais
        assertThat(msgA1).isEqualTo(msgA2);
        assertThat(msgA1.hashCode()).isEqualTo(msgA2.hashCode());

        // Comparações com falha (valores diferentes ou tipos incompatíveis)
        assertThat(msgA1).isNotEqualTo(msgB);
        assertThat(msgA1).isNotEqualTo(null);
        assertThat(msgA1).isNotEqualTo("Bem-vindo ao E-commerce"); // String pura não é um objeto Message
    }
}