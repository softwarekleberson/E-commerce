# Módulo `recommendation`

## Responsabilidade e fluxo

Entrega recomendações determinísticas por filtros no endpoint `GET /customer/recommendations` e oferece um chat conversacional integrado ao serviço RAG opcional. O endpoint estruturado usa a identidade autenticada para resolver o cliente e devolve recomendações com justificativa.

`RecommendationServiceImpl` consulta pedidos pagos e wishlist, busca candidatos ativos no catálogo com os filtros recebidos, descarta produtos sem estoque e ordena os restantes. A wishlist tem maior peso; categorias compradas e repetição de itens em compras pagas também influenciam a pontuação. Cada resultado inclui o motivo, preço e quantidade disponível observados durante a consulta.

O processamento considera até 100 pedidos e 100 candidatos por chamada e aceita entre 1 e 20 resultados. A disponibilidade pode mudar após a resposta, portanto o estoque deve ser validado novamente em operações posteriores, como adicionar ao carrinho.

## Feedback do chat

O endpoint conversacional retorna um `responseId` por resposta. `POST /customer/recommendations/chat/{responseId}/feedback` recebe `{"liked":true}` ou `{"liked":false}` e persiste a avaliação vinculada ao email autenticado. O usuário só pode avaliar respostas próprias; somente o identificador, email e voto são armazenados, não o texto da conversa.

Para personalizar o chat, o backend consulta até 100 pedidos pagos do cliente autenticado, agrega quantidades por categoria e envia ao RAG no máximo cinco categorias, sem IDs ou nomes de produtos comprados. O histórico é um sinal secundário tanto para a busca vetorial quanto para a geração da resposta; o pedido atual continua prioritário.

## Dependências

- `order`: consulta de histórico do cliente.
- `wishlist`: consulta de produtos desejados.
- `product`: catálogo ativo e pesquisa por categoria, preço e moeda.
- `stock`: quantidade disponível por produto.
- `shared`: resolução da identidade do cliente.

O endpoint é protegido pelas regras atuais de `/customer/**`; o identificador do cliente não vem dos parâmetros da requisição.

## Referências

- [Guia local do módulo](../../../src/main/java/com/cleancode/ecommerce/recommendation/README.md)
- [Controller](../../../src/main/java/com/cleancode/ecommerce/recommendation/infra/controller/RecommendationController.java)
- [Serviço de recomendação](../../../src/main/java/com/cleancode/ecommerce/recommendation/application/usecase/RecommendationServiceImpl.java)
- [Módulo order](order.md)
- [Módulo wishlist](wishlist.md)
- [Módulo product](product.md)
- [Módulo stock](stock.md)
