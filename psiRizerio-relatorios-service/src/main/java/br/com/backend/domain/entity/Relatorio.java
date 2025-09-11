package br.com.backend.domain.entity;

import java.util.Date;
import java.util.UUID;

public class Relatorio {

    private UUID id;
    private Date data;
    private String conteudo;
    private Integer fkSessao;
    private Integer fkPaciente;
}
