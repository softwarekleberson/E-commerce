# Módulo `recommendation`

## Responsabilidade

Gera recomendações iniciais por regras, usando compras pagas e lista de desejos do cliente. Esta etapa não integra IA generativa nem interpreta mensagens livres de chat.

## Estrutura e fluxo

- `application/dto/`: formato da recomendação, com produto, preço, estoque disponível e motivo.
- `application/usecase/`: contrato e implementação da ordenação por sinais de preferência.
- `infra/controller/`: endpoint autenticado de recomendações.
- `infra/config/`: composição do serviço com os repositórios atuais.

`GET /customer/recommendations` usa o email da autenticação e aceita `category`, `maximumPrice`, `typeCoin` e `limit`. A resposta contém apenas produtos ativos encontrados no catálogo e com estoque disponível. Produtos na wishlist recebem prioridade; compras pagas influenciam a categoria e itens comprados anteriormente recebem um sinal adicional. Sem sinais pessoais, os resultados elegíveis aparecem como opções para explorar.

O processamento consulta até 100 pedidos e 100 produtos candidatos por chamada; o limite de resposta aceito é de 1 a 20. O cliente não informa seu próprio identificador: a identidade vem da sessão autenticada.

## Integrações

- `order`: histórico de pedidos do cliente.
- `wishlist`: itens desejados.
- `product`: catálogo ativo e filtros de categoria/preço.
- `stock`: quantidade disponível.
- `shared`: resolução do ID do cliente a partir da identidade autenticada.

## Referências

- [Controller](infra/controller/RecommendationController.java)
- [Serviço e ordenação](application/usecase/)
- [Guia do sistema](../../../../../../doc/docs/architecture/recommendation.md)
