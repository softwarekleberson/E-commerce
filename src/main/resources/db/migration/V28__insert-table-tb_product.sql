-- Inserções na tabela tb_product
INSERT INTO tb_product (
    product_id, product_type, active, name, description, 
    price, type_coin, category, brand, pricing, 
    justification, product_status, created_at, updated_at
) VALUES 
(
    'cf6011b2-e36b-478a-b177-eddb6470600d', 'book', 1, 'Clean Code', 
    'Mesmo um código ruim pode funcionar. Mas se ele não for limpo, pode acabar com uma empresa de desenvolvimento.', 
    22.50, 'DOLAR', 'BOOKS', 'Pearson Education', 0.50, 
    NULL, NULL, '2026-10-06 20:18:17', '2026-10-06 20:20:57'
);


-- Inserções na tabela tb_book
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


-- Inserções na tabela tb_midia
INSERT INTO tb_midia (
    midia_id, url, description, product_id
) VALUES 
(
    '696c386e-cae0-41b0-9efb-d60799c5832a', 
    'https://m.media-amazon.com/images/I/71nj3JM-igL._SY385_.jpg', 
    'Product By Student and other peoples', 
    'cf6011b2-e36b-478a-b177-eddb6470600d'
);