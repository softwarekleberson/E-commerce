# Serviço RAG de recomendações

Este serviço é uma integração opcional do módulo Java `recommendation`. O endpoint anterior `GET /customer/recommendations` e sua implementação não são alterados.

## Executar

Crie uma chave no Google AI Studio e salve-a em `ai-recommendation/.env` (use `.env.example` como modelo). Depois inicie o serviço:

```bash
cd ai-recommendation
cp .env.example .env
# Edite .env e substitua o valor de GEMINI_API_KEY pela sua chave.
docker compose -f compose.yml up --build -d
```

O serviço usa Gemini para gerar embeddings e respostas, e FAISS CPU para busca vetorial. O índice é persistido no volume Docker `faiss_data`.

O backend Java deve alcançar o serviço em `http://localhost:8000`. Ajuste `RECOMMENDATION_RAG_BASE_URL` se estiver em outra rede ou endereço. O backend envia o catálogo ativo no início e verifica mudanças a cada 12 horas; só atualiza o índice se detectar alterações. Configure `recommendation.rag.index-refresh-delay-ms` para mudar esse intervalo.

## API conversacional

`POST /customer/recommendations/chat` é autenticado pelas regras existentes de `/customer/**`:

```json
{"message":"Quero um presente de ficção científica até 100 reais"}
```

A resposta inclui texto gerado e recomendações estruturadas. O Java verifica novamente se cada produto continua ativo e disponível em estoque. A busca vetorial seleciona até cinco candidatos.

Antes da busca, o serviço classifica a mensagem e só permite intenção de buscar, comparar ou pedir recomendações de produtos. Para outros assuntos, responde com uma mensagem fixa sem consultar o catálogo nem gerar uma resposta livre. Se não conseguir classificar a mensagem, recusa a solicitação.

A cada chamada do chat, o backend considera até 100 pedidos pagos, agrega os produtos comprados e envia ao RAG somente as categorias mais frequentes (até cinco), com contagens. IDs de cliente/pedido e nomes dos produtos comprados não são enviados. Esse resumo influencia a busca vetorial e a geração, sempre como sinal secundário ao pedido atual.

A API Java inclui um `responseId` na resposta do chat. Para registrar feedback, envie `POST /customer/recommendations/chat/{responseId}/feedback` com `{"liked":true}` (curtir) ou `{"liked":false}` (descurtir). A avaliação exige autenticação e só pode ser alterada pelo usuário que recebeu aquela resposta.

## Configuração

- `GEMINI_API_KEY`: chave do Google AI Studio, necessária para embeddings e geração de respostas.
- `GEMINI_CHAT_MODEL`: modelo Gemini usado para classificar e responder (padrão `gemini-3.8-flash`).
- `GEMINI_EMBEDDING_MODEL`: modelo de embeddings (padrão `gemini-embedding-001`).
- `FAISS_DATA_DIR`: diretório persistido do índice (padrão `/data`).
- `RECOMMENDATION_RAG_BASE_URL`: URL do serviço vista pelo backend Java (padrão `http://localhost:8000`).

Se o serviço Python ou a chave não estiverem disponíveis, o endpoint novo retorna erro de integração; os endpoints anteriores de recomendação continuam independentes.
