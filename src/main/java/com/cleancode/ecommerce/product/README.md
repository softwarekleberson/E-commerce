# Módulo `product`

## Responsabilidade

Gerencia catálogo, características de livros e bolsas, preço, mídia e ativação de produtos.

## Estrutura e arquitetura

- `application/cache/`: nomes de cache usados pela aplicação.
- `application/dto/input/` e `output/`: modelos de criação/alteração e respostas específicas de produto, livro e bolsa.
- `application/service/` e `service/contract/`: serviços e contratos de ativação/preço.
- `application/usecase/` e `usecase/contract/`: operações de produto e contratos de entrada.
- `domain/`: `Product`, propriedades comuns, políticas, status, exceções e repositório.
- `domain/bag/` e `domain/books/`: atributos específicos de bolsas e livros.
- `infra/config/`, `controller/`, `gateway/`, `mapper/`, `persistence/`: composição, HTTP, adapter, conversão e entidades. Mappers/persistências de bolsa e livro têm subpastas próprias.

## Casos de uso encontrados

- Criar produto: `CreateProductImpl`.
- Ativar produto automaticamente ou manualmente: `ActiveProductImpl`, `ManualProductActivationImpl`.
- Desativar manualmente: `ManualProductDeactivationImpl`.
- Revisar detalhes: `ReviseDetailsImpl`.
- Listar produtos ativos, inativos e buscar ativo por ID: `ListAllProductActiveImpl`, `ListAllProductsInactiveImpl`, `ListProductActivetByIdImpl`.
- Ajustar preço conforme margem/estoque: `IncreaseSellingPriceAboveProfitMarginImpl`, `SellingPriceRelativeToInventory`.

`ProductStatusPolicy` concentra política de status. As rotas estão divididas entre `ProductController` e `ProductListController`; verifique mappings nessas classes para os caminhos HTTP vigentes.

## Referências

- [Controllers](infra/controller/)
- [Use cases](application/usecase/)
- [Domínio](domain/)
- [Persistência e mappers](infra/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/product.md)
