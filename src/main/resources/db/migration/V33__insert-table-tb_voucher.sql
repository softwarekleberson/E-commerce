INSERT INTO tb_voucher (
    voucher_id,
    customer_id,
    message,
    emission,
    type_voucher,
    discount,
    active
) VALUES 
(
    'a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', -- Gerado novo UUID
    'f2a84230-2417-4088-bc2a-872683fc3b54',
    'Teste para o pagamento cupom 3.',
    '2026-10-06',
    'PROMOTIONAL',
    50,
    1
),
(
    'b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e', -- Gerado novo UUID
    'f2a84230-2417-4088-bc2a-872683fc3b54',
    'Teste para o pagamento cupom 2.',
    '2026-10-06',
    'PROMOTIONAL',
    100,
    1
),
(
    'c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f', -- Gerado novo UUID
    'f2a84230-2417-4088-bc2a-872683fc3b54',
    'Teste para o pagamento cupom 1.',
    '2026-10-06',
    'PROMOTIONAL',
    50,
    1
),
(
    'd4e5f6a7-b8c9-0d1e-2f3a-4b5c6d7e8f9a', -- Gerado novo UUID
    'f2a84230-2417-4088-bc2a-872683fc3b54',
    'Teste para o pagamento cupom 0.',
    '2026-10-06',
    'PROMOTIONAL',
    10,
    1
);