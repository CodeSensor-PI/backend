CREATE TABLE IF NOT EXISTS telefone(
    id int primary key auto_increment,
    fk_cliente int not null,
    ddd char(2) not null,
    numero varchar(14) not null,
    tipo varchar(15) not null check(tipo in('emergencial', 'pessoal')),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP,
    constraint cliente_telefone_fk foreign key(fk_cliente) references cliente(id)
);