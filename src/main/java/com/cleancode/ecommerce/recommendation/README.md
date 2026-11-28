# Módulo `recommendation`

## Responsabilidade

Oferece recomendações por regras no endpoint estruturado e recomendações conversacionais pelo serviço RAG opcional.

## Estrutura e fluxo

- `application/dto/`: formato da recomendação, com produto, preço, estoque disponível e motivo.
- `application/usecase/`: contrato e implementação da ordenação por sinais de preferência.
- `infra/controller/`: endpoint autenticado de recomendações.
- `infra/config/`: composição do serviço com os repositórios atuais.

`GET /customer/recommendations` usa o email da autenticação e aceita `category`, `maximumPrice`, `typeCoin` e `limit`. A resposta contém apenas produtos ativos encontrados no catálogo e com estoque disponível. Produtos na wishlist recebem prioridade; compras pagas influenciam a categoria e itens comprados anteriormente recebem um sinal adicional. Sem sinais pessoais, os resultados elegíveis aparecem como opções para explorar.

`POST /customer/recommendations/chat` integra o chat com o serviço RAG. O endpoint e o contrato de feedback são descritos abaixo.

O processamento consulta até 100 pedidos e 100 produtos candidatos por chamada; o limite de resposta aceito é de 1 a 20. O cliente não informa seu próprio identificador: a identidade vem da sessão autenticada.
O chat considera até 100 pedidos pagos e envia ao RAG somente contagens agregadas das categorias compradas mais frequentemente (até cinco categorias), como sinal secundário ao pedido atual.

## Integrações

- `order`: histórico de pedidos do cliente.
- `wishlist`: itens desejados.
- `product`: catálogo ativo e filtros de categoria/preço.
- `stock`: quantidade disponível.
- `shared`: resolução do ID do cliente a partir da identidade autenticada.

## Chat com feedback

`POST /customer/recommendations/chat` retorna também um `responseId`, usado para avaliar aquela resposta:

```json
{"message":"Quero um presente de ficção científica"}
```

Para registrar ou alterar a avaliação, envie `POST /customer/recommendations/chat/{responseId}/feedback` com
`{"liked":true}` para curtir ou `{"liked":false}` para descurtir. A avaliação é associada ao usuário autenticado;
respostas de outros usuários não podem ser avaliadas. O backend persiste somente o identificador da resposta,
o email autenticado e a avaliação, sem armazenar o texto do chat.

## Referências

- [Controller](infra/controller/RecommendationController.java)
- [Serviço e ordenação](application/usecase/)
- [Guia do sistema](../../../../../../doc/docs/architecture/recommendation.md)
