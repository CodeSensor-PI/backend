CREATE TABLE IF NOT EXISTS avaliacao(
	id int primary key auto_increment,
    nota int not null check (nota between 1 and 5),
    feedback longtext
);

CREATE SEQUENCE AVALIACAO_SEQ START WITH 1 INCREMENT BY 1;