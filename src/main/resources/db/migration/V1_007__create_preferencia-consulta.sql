CREATE TABLE IF NOT EXISTS preferencia_consulta(
    id int auto_increment,
    fk_cliente int not null,
    frequencia varchar(10) not null check(frequencia in('semanal', 'quinzenal')),
    dia_semana varchar(10) not null check(dia_semana in('segunda', 'terça', 'quarta', 'quinta', 'sexta', 'sábado', 'domingo')),
    horario varchar(5) not null,
    plataforma_atendimento varchar(15) not null check(plataforma_atendimento in('whatsapp', 'meet')),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP,
    primary key (id, fk_cliente),
    constraint cliente_preferenciaConsulta_fk foreign key(fk_cliente) references cliente(id)
);