package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.enums.TipoSessao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessao")
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_cliente", columnDefinition = "INT", nullable = false)
    private Usuario fkCliente;

    @Column(name = "dt_hr_sessao", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime dtHrSessao;

    @Column(name = "tipo", columnDefinition = "VARCHAR(10)", nullable = false)
    private TipoSessao tipo;

    @Column(name = "status_sessao", columnDefinition = "VARCHAR(15)", nullable = false)
    private StatusSessao statusSessao;

    @Column(name = "anotacao", columnDefinition = "LONGTEXT", nullable = false)
    private String anotacao;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Usuario fkCliente) {
        this.fkCliente = fkCliente;
    }

    public LocalDateTime getDtHrSessao() {
        return dtHrSessao;
    }

    public void setDtHrSessao(LocalDateTime dtHrSessao) {
        this.dtHrSessao = dtHrSessao;
    }

    public TipoSessao getTipo() {
        return tipo;
    }

    public void setTipo(TipoSessao tipo) {
        this.tipo = tipo;
    }

    public StatusSessao getStatusSessao() {
        return statusSessao;
    }

    public void setStatusSessao(StatusSessao statusSessao) {
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
