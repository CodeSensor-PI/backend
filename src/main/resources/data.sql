-- Inserir plano apenas se não existir
INSERT INTO plano (id, categoria, preco)
SELECT 1, 'Mensal', 400.00
    WHERE NOT EXISTS (SELECT 1 FROM plano WHERE id = 1);

INSERT INTO plano (id, categoria, preco)
SELECT 2, 'Semanal', 100.00
    WHERE NOT EXISTS (SELECT 1 FROM plano WHERE id = 2);

-- Inserir endereço apenas se não existir
INSERT INTO endereco (id, cep, logradouro, numero, bairro, cidade, uf, created_at, updated_at)
SELECT 1, '12345678', 'Rua Exemplo', '123', 'Centro', 'São Paulo', 'SP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM endereco WHERE id = 1);

-- Inserir roles apenas se não existir
INSERT INTO roles (id, role)
SELECT 1, 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE id = 1);

INSERT INTO roles (id, role)
SELECT 2, 'PACIENTE'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE id = 2);

INSERT INTO roles (id, role)
SELECT 3, 'PSICOLOGO'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE id = 3);