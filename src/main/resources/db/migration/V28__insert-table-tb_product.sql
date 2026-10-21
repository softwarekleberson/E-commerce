-- Inserir produtos
INSERT INTO tb_product (
    product_id,
    product_type,
    active,
    name,
    description,
    price,
    type_coin,
    category,
    brand,
    pricing,
    justification,
    product_status,
    created_at,
    updated_at
) VALUES
(
    'a0e16dd4-2cd8-465d-b79d-094e3510cdf5',
    'bag',
    1,
    'Mochila de treino',
    'Mochila impermeável com compartimento para notebook de 15 polegadas',
    16.50,
    'DOLAR',
    'BAG',
    'Clean Fashion Wear',
    0.10,
    NULL,
    NULL,
    '2026-10-20 13:52:51',
    '2026-10-20 13:54:28'
),
(
    'cf6011b2-e36b-478a-b177-eddb6470600d',
    'book',
    1,
    'Clean Code',
    'Mesmo um código ruim pode funcionar. Mas se ele não for limpo, pode acabar com uma empresa de desenvolvimento.',
    22.50,
    'DOLAR',
    'BOOKS',
    'Pearson Education',
    0.50,
    NULL,
    NULL,
    '2026-10-06 20:18:17',
    '2026-10-06 20:20:57'
);

-- Inserir livro
INSERT INTO tb_book (
    product_id,
    synopsis,
    page,
    author,
    edition,
    isbn,
    category_book,
    height,
    width,
    length,
    weight,
    publisher_date
) VALUES (
    'cf6011b2-e36b-478a-b177-eddb6470600d',
    'Um guia completo sobre desenvolvimento de software, abordando princípios de programação, arquitetura e boas práticas.',
    400,
    'Robert C. Martin',
    '1ª edição',
    '9780132350884',
    'NON_FICTION',
    23.00,
    18.00,
    3.00,
    0.70,
    '2008-08-01'
);

-- Inserir mochila
INSERT INTO tb_bag (
    product_id,
    volume,
    color
) VALUES (
    'a0e16dd4-2cd8-465d-b79d-094e3510cdf5',
    25.50,
    'Preto Fosco'
);

-- Inserir mídias
INSERT INTO tb_midia (
    midia_id,
    url,
    description,
    product_id
) VALUES
(
    '2da2f84b-e17a-4814-940c-09a0f02dd4e3',
    'https://m.media-amazon.com/images/I/610-knF9KoL._AC_SX522_.jpg',
    'Escoteiro Jute Bag',
    'a0e16dd4-2cd8-465d-b79d-094e3510cdf5'
),
(
    '696c386e-cae0-41b0-9efb-d60799c5832a',
    'https://m.media-amazon.com/images/I/71nj3JM-igL._SY385_.jpg',
    'Product By Student and other peoples',
    'cf6011b2-e36b-478a-b177-eddb6470600d'
);