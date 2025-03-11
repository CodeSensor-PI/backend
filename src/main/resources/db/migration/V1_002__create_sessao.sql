CREATE TABLE IF NOT EXISTS sessao(
    id int primary key auto_increment,
    fk_cliente int not null,
    dt_hr_sessao datetime not null,
    tipo varchar(10) not null check(tipo in('avulso', 'plano')),
    status_sessao varchar(15) not null check(status_sessao in('cancelada', 'concluida', 'aguardando')),
    anotacao longtext,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP,
    constraint cliente_sessao_fk foreign key(fk_cliente) references cliente(id)
);