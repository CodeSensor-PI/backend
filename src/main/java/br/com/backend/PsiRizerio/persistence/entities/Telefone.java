package br.com.backend.PsiRizerio.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telefone_seq")
    @SequenceGenerator(name = "telefone_seq", sequenceName = "TELEFONE_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "ddd", columnDefinition = "CHAR(2)")
    private String ddd;

    @Column(name = "numero", columnDefinition = "CHAR(14)")
    private String numero;

    @Column(name = "tipo", columnDefinition = "VARCHAR(15)")
    private String tipo;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "fk_cliente", columnDefinition = "INT")
    @ManyToOne
    @JsonIgnore
    private Usuario fkCliente;

    public Telefone() {
    }

    public Telefone(Integer id, String ddd, String numero, String tipo, LocalDateTime createdAt, LocalDateTime updatedAt, Usuario fkCliente) {
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
        this.tipo = tipo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.fkCliente = fkCliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Usuario getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Usuario fkCliente) {
        this.fkCliente = fkCliente;
    }
}
