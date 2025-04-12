INSERT INTO plano (categoria, preco) VALUES
                                         ('Básico', 29.90),
                                         ('Intermediário', 49.90),
                                         ('Premium', 79.90);

INSERT INTO endereco (cep, logradouro, bairro, numero, cidade, uf) VALUES
                                                                       ('01234-567', 'Rua das Flores', 'Jardim Primavera', '123', 'São Paulo', 'SP'),
                                                                       ('09876-543', 'Av. Brasil', 'Centro', '456', 'Rio de Janeiro', 'RJ'),
                                                                       ('12345-678', 'Rua da Paz', 'Boa Vista', '789', 'Curitiba', 'PR');

INSERT INTO cliente (nome, cpf, email, senha, fk_plano, fk_endereco) VALUES
                                                                         ('Ana Souza', '123.456.789-00', 'ana.souza@email.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', 1, 1),
                                                                         ('Carlos Lima', '987.654.321-00', 'carlos.lima@email.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', 2, 2),
                                                                         ('Mariana Rocha', '456.123.789-00', 'mariana.rocha@email.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', 3, 3);