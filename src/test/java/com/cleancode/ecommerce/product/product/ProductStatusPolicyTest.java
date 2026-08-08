package com.cleancode.ecommerce.product.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.ProductStatusCategory;
import com.cleancode.ecommerce.product.domain.ProductStatusPolicy;

class ProductStatusPolicyTest {

    @Test
    @DisplayName("Must create an activation policy with correct justification and category")
    void shouldCreateActivationPolicySuccessfully() {
        ProductStatusPolicy policy = ProductStatusPolicy.activation("Produto pronto para venda", ProductStatusCategory.PUBLIC_PETITION);

        assertThat(policy).isNotNull();
        assertThat(policy.getJustification()).isEqualTo("Produto pronto para venda");
        assertThat(policy.getCategory()).isEqualTo(ProductStatusCategory.PUBLIC_PETITION);
    }

    @Test
    @DisplayName("Should create a manual deactivation policy with the correct justification and category")    void shouldCreateManualDeactivationPolicySuccessfully() {
        ProductStatusPolicy policy = ProductStatusPolicy.manualDeactivation("Falta de insumos", ProductStatusCategory.PUBLIC_PETITION);

        assertThat(policy).isNotNull();
        assertThat(policy.getJustification()).isEqualTo("Falta de insumos");
        assertThat(policy.getCategory()).isEqualTo(ProductStatusCategory.PUBLIC_PETITION);
    }

    @Test
    @DisplayName("Should create an automatic deactivation policy assigning the OUT_OF_MARKET category")
    void shouldCreateAutomaticDeactivationPolicySuccessfully() {
        ProductStatusPolicy policy = ProductStatusPolicy.automaticDeactivation();

        assertThat(policy).isNotNull();
        assertThat(policy.getJustification()).isNull(); // Construtor de um argumento não atribui justificativa
        assertThat(policy.getCategory()).isEqualTo(ProductStatusCategory.OUT_OF_MARKET);
    }

    @Test
    @DisplayName("Must validate equality (equals and hashCode) based on internal values")
    void shouldRespectEqualsAndHashCodeContracts() {
        ProductStatusPolicy policy1 = ProductStatusPolicy.activation("Justificativa A", ProductStatusCategory.PUBLIC_PETITION);
        ProductStatusPolicy policy2 = ProductStatusPolicy.manualDeactivation("Justificativa A", ProductStatusCategory.PUBLIC_PETITION);
        ProductStatusPolicy policyDifferent = ProductStatusPolicy.activation("Justificativa B", ProductStatusCategory.PUBLIC_PETITION);

        // Mesmo vindo de métodos de fábrica diferentes, se o conteúdo interno for igual, o objeto é considerado igual
        assertThat(policy1).isEqualTo(policy2);
        assertThat(policy1.hashCode()).isEqualTo(policy2.hashCode());

        // Conteúdos diferentes
        assertThat(policy1).isNotEqualTo(policyDifferent);
        assertThat(policy1).isNotEqualTo(null);
        assertThat(policy1).isNotEqualTo("uma string qualquer");
    }
}