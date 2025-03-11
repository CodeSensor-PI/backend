CREATE TABLE IF NOT EXISTS plano(
    id int primary key auto_increment,
    categoria varchar(25) not null,
    preco decimal(6,2) not null
);

CREATE SEQUENCE PLANO_SEQ START WITH 1 INCREMENT BY 1;
