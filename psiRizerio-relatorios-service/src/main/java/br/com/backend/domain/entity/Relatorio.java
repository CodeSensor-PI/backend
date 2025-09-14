package br.com.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
public class Relatorio {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private Date data;
    private String conteudo;
    @Column(name = "fk_sessao")
    private Integer fkSessao;

    @Column(name = "fk_paciente")
    private Integer fkPaciente;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Integer getFkSessao() {
        return fkSessao;
    }

    public void setFkSessao(Integer fkSessao) {
        this.fkSessao = fkSessao;
    }

    public Integer getFkPaciente() {
        return fkPaciente;
    }

    public void setFkPaciente(Integer fkPaciente) {
        this.fkPaciente = fkPaciente;
    }
}
