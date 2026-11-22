-- =====================================================
-- CUSTOMER
-- =====================================================

INSERT INTO tb_customer (
    customer_id,
    system_client_status,
    cpf,
    full_name,
    birth_date,
    password_hash,
    gender,
    area_code,
    phone_number,
    phone_type,
    email,
    created_at,
    updated_at
) VALUES (
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    1,
    '428.067.429-40',
    'pessoal teste',
    '1994-10-10',
    '$2a$10$BUIl3QRD6V.LnCQZRZGiCexsc0kaAiTzH1s7ZzokdoCbcuh12WaTq',
    'MALE',
    '11',
    '987654321',
    'MOBILE',
    'pessoa.teste@gmail.com',
    '2026-10-14 18:10:15',
    '2026-10-14 18:10:15'
);


-- =====================================================
-- ADDRESS
-- =====================================================

INSERT INTO tb_address (
    address_id,
    public_id,
    main,
    receiver,
    street,
    `number`,
    neighborhood,
    zip_code,
    observation,
    street_type,
    residence_type,
    city,
    state,
    country,
    customer_id
) VALUES (
    1,
    '21b9cd17-402b-4597-83b0-c1902d61eeab',
    1,
    'pessoal teste',
    'Avenida Paulista',
    '1000',
    'Bela Vista',
    '01310100',
    'Apartamento 42, bloco B',
    'Avenida',
    'Apartamento',
    'São Paulo',
    'SP',
    'Brasil',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b'
);


-- =====================================================
-- DELIVERY
-- =====================================================

INSERT INTO tb_delivery (
    delivery_id,
    address_id,
    delivery_phrase
) VALUES (
    1,
    1,
    'Deixar na portaria com o zelador'
);


-- =====================================================
-- CARDS
-- =====================================================

INSERT INTO tb_card (
    card_id,
    customer_id,
    main,
    printed_name,
    code,
    number_card,
    expiration_date,
    flag
) VALUES
(
    '2574085f-2239-47fe-a7fc-2c8e600906c3',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    1,
    'CHARLE FRANCO',
    '159',
    '4659460256485170',
    '2030-12-31',
    'MASTERCARD'
),
(
    'baeda08f-4d4c-4d8b-ad11-48ad995b975a',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    0,
    'CHARLE ANDERSEN',
    '159',
    '4514169443299080',
    '2035-06-20',
    'MASTERCARD'
);


-- =====================================================
-- VOUCHERS
-- =====================================================

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
    'a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    'Teste para o pagamento cupom 3.',
    '2026-10-06',
    'PROMOTIONAL',
    50,
    1
),
(
    'b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    'Teste para o pagamento cupom 2.',
    '2026-10-06',
    'PROMOTIONAL',
    100,
    1
),
(
    'c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    'Teste para o pagamento cupom 1.',
    '2026-10-06',
    'PROMOTIONAL',
    50,
    1
),
(
    'd4e5f6a7-b8c9-0d1e-2f3a-4b5c6d7e8f9a',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    'Teste para o pagamento cupom 0.',
    '2026-10-06',
    'PROMOTIONAL',
    10,
    1
),
(
    'e4e5f6a7-b8c9-0d1e-2f3a-4b5c6d7e8f9a',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    'Teste para o pagamento cupom 0.',
    '2026-10-06',
    'PROMOTIONAL',
    30,
    1
),
(
    'f4e5f6a7-b8c9-0d1e-2f3a-4b5c6d7e8f9a',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    'Teste para o pagamento cupom 0.',
    '2026-10-06',
    'REPLACEMENT',
    100,
    1
),
(
    'g4e5f6a7-b8c9-0d1e-2f3a-4b5c6d7e8f9a',
    '40ebef6f-d8b2-4a7f-b4e9-8b16909cd57b',
    'Teste para o pagamento cupom 0.',
    '2026-10-06',
    'PROMOTIONAL',
     24,
    1
);