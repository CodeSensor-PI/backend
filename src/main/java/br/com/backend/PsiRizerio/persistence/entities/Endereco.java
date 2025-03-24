package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "cep", columnDefinition = "CHAR(9)", nullable = false)
    private String cep;

    @Column(name = "logradouro", columnDefinition = "VARCHAR(60)", nullable = false)
    private String logradouro;

    @Column(name = "numero", columnDefinition = "VARCHAR(5)", nullable = false)
    private Integer numero;

    @Column(name = "cidade", columnDefinition = "VARCHAR(25)", nullable = false)
    private String cidade;

    @Column(name = "bairro", columnDefinition = "VARCHAR(40)", nullable = false)
    private String bairro;

    @Column(name = "uf", columnDefinition = "CHAR(2)", nullable = false)
    private String uf;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    public Endereco(Integer id, String cep, String logradouro, Integer numero, String cidade, String bairro, String uf, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.bairro = bairro;
        this.uf = uf;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Endereco() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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
