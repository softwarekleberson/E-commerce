# Módulo `product`

## Responsabilidade e estrutura

Mantém catálogo, variantes e políticas de preço/ativação. `application/dto/input` e `output` separam modelos de entrada/saída; `application/service` coordena preço e ativação; `application/usecase` implementa operações; `application/cache` define nomes de cache; `domain/bag` e `domain/books` detalham tipos específicos; `infra` contém controllers, configuração, gateway, mappers e entidades JPA.

## Casos de uso identificados

- Criar produto: `CreateProductImpl`.
- Ativar automaticamente: `ActiveProductImpl`; serviço correlato `ProductActivationServiceImpl`.
- Ativar/desativar manualmente: `ManualProductActivationImpl`, `ManualProductDeactivationImpl`.
- Revisar detalhes: `ReviseDetailsImpl`.
- Consultar ativos/inativos e por identificador: `ListAllProductActiveImpl`, `ListAllProductsInactiveImpl`, `ListProductActivetByIdImpl`.
- Ajustar preço por margem e estoque: `IncreaseSellingPriceAboveProfitMarginImpl`, `SellingPriceRelativeToInventory`.

Os controllers `ProductController` e `ProductListController` expõem endpoints; a separação exata de rotas deve ser conferida nos mappings atuais. `ProductStatusPolicy` é a política do domínio para status. Produto se especializa em `Bag` e `Book`; os mappers dessas variantes ficam em subpastas distintas.

## Referências

- [Controllers](../../../src/main/java/com/cleancode/ecommerce/product/infra/controller/)
- [Use cases](../../../src/main/java/com/cleancode/ecommerce/product/application/usecase/)
- [Política e entidades de domínio](../../../src/main/java/com/cleancode/ecommerce/product/domain/)
- [Mappers e persistência](../../../src/main/java/com/cleancode/ecommerce/product/infra/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/product/)

