-- Inserir plano
INSERT INTO plano (id, categoria, preco) VALUES (1, 'Mensal', 400.00),
(2, 'Semanal', 100.00);

-- Inserir endereço
INSERT INTO endereco (id, cep, logradouro, numero, bairro, cidade, uf, created_at, updated_at)
VALUES (1, '12345678', 'Rua Exemplo', '123', 'Centro', 'São Paulo', 'SP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO roles (id, role) VALUES (1, 'ADMIN');
INSERT INTO roles (id, role) VALUES (2, 'PACIENTE');
INSERT INTO roles (id, role) VALUES (3, 'PSICOLOGO');