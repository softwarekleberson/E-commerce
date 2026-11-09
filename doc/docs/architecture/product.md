# Módulo `product`

## Responsabilidade e estrutura

Mantém catálogo, variantes e políticas de preço/ativação. `application/dto/input` e `output` separam modelos de entrada/saída; `application/service` coordena preço e ativação; `application/usecase` implementa operações; `infra/service/cache` define nomes e serviços de cache. O domínio comum fica em `domain/`, com tipos específicos em `domain/bag`, `domain/books` e `domain/toy`; `infra` contém controllers, configuração, gateway, mappers e entidades JPA.

## Casos de uso identificados

- Criar produto: `CreateProductImpl`.
- Ativar automaticamente: `ActiveProductImpl`; serviço correlato `ProductActivationServiceImpl`.
- Ativar/desativar manualmente: `ManualProductActivationImpl`, `ManualProductDeactivationImpl`.
- Revisar detalhes: `ReviseDetailsImpl`.
- Consultar ativos/inativos e por identificador: `ListAllProductActiveImpl`, `ListAllProductsInactiveImpl`, `ListProductActivetByIdImpl`.
- Ajustar preço por margem e estoque: `IncreaseSellingPriceAboveProfitMarginImpl`, `SellingPriceRelativeToInventory`.

Os controllers `ProductController` e `ProductListController` expõem endpoints; a separação exata de rotas deve ser conferida nos mappings atuais. `ProductStatusPolicy` é a política do domínio para status. `Product` possui as variantes `Bag`, `Book` e `Toy`; seus atributos especializados e mapeamentos estão em subpastas próprias (`bag`, `book`/`books` e `toy`). Para brinquedos, `CreateToyDto` constrói `Toy`, `ProductDtoFactory` produz `ListToyDto`, e `ToyMapper` converte entre domínio e `ToyEntity`, persistida em `tb_toy` pela migração `V36__create_table_tb_toy.sql`. A entidade usa a categoria `TOYS` e armazena tipo, faixa etária, material, quantidade de peças e informações de bateria. Não foi encontrado caso de teste específico de brinquedo em `src/test/java`.

## Referências

- [Controllers](../../../src/main/java/com/cleancode/ecommerce/product/infra/controller/)
- [Use cases](../../../src/main/java/com/cleancode/ecommerce/product/application/usecase/)
- [Política e entidades de domínio](../../../src/main/java/com/cleancode/ecommerce/product/domain/)
- [Mappers e persistência](../../../src/main/java/com/cleancode/ecommerce/product/infra/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/product/)

