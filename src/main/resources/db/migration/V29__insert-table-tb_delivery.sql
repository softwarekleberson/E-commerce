INSERT INTO tb_address (
    address_id,
    public_id,
    main,
    receiver,
    street,
    number,
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
    'rua dos Brasileiros',
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

INSERT INTO tb_delivery (
    delivery_id,
    address_id,
    delivery_phrase
) VALUES (
    1,
    1,
    'Deixar na portaria com o zelador'
);
