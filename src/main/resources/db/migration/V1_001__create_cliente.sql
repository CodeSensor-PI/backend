CREATE TABLE IF NOT EXISTS cliente(
    id int primary key auto_increment,
    nome varchar(60) not null,
    cpf char(14) not null,
    email varchar(80) not null,
    senha varchar(20) not null,
    fk_plano int,
    fk_endereco int not null,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP,
    constraint plano_cliente_fk foreign key(fk_plano) references plano(id),
    constraint endereco_cliente_fk foreign key(fk_endereco) references endereco(id)
);