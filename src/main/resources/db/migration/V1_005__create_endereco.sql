CREATE TABLE IF NOT EXISTS endereco(
    id int primary key auto_increment,
    cep char(9) not null,
    logradouro varchar(60) not null,
    bairro varchar(40) not null,
    numero varchar(5) not null,
    cidade varchar(25) not null,
    uf char(2) not null,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP
);