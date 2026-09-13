-- Insert na tabela tb_address
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
) 
VALUES (
    1, 
    '4782b478-4267-4675-b614-0732611937a4', 
    1, 
    'Inglaterra Pipoca', 
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
    'f2a84230-2417-4088-bc2a-872683fc3b54'
);

-- Insert na tabela tb_delivery
INSERT INTO tb_delivery (
    delivery_id, 
    address_id, 
    delivery_phrase
) 
VALUES (
    1, 
    1, 
    'Deixar na portaria com o zelador'
);