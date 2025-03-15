package br.com.backend.PsiRizerio.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@Entity
@Builder
@Table(name = "sessao")
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
    private Integer id;

    @JoinColumn(name = "fk_cliente", columnDefinition = "INT", nullable = false)
    private Integer fkCliente;

    @Column(name = "dt_hr_sessao", columnDefinition = "DATETIME", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtHrSessao;

    @Column(name = "tipo", columnDefinition = "VARCHAR(10)", nullable = false)
    private String tipo;

    @Column(name = "status_sessao", columnDefinition = "VARCHAR(15)", nullable = false)
    private String statusSessao;

    @Column(name = "anotacao", columnDefinition = "LONGTEXT", nullable = false)
    private String anotacao;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    public Sessao(Integer id, Integer fkCliente, LocalDateTime dtHrSessao, String tipo, String statusSessao, String anotacao, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fkCliente = fkCliente;
        this.dtHrSessao = dtHrSessao;
        this.tipo = tipo;
        this.statusSessao = statusSessao;
        this.anotacao = anotacao;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Sessao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Integer fkCliente) {
        this.fkCliente = fkCliente;
    }

    public LocalDateTime getDtHrSessao() {
        return dtHrSessao;
    }

    public void setDtHrSessao(LocalDateTime dtHrSessao) {
        this.dtHrSessao = dtHrSessao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatusSessao() {
        return statusSessao;
    }

    public void setStatusSessao(String statusSessao) {
        this.statusSessao = statusSessao;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
