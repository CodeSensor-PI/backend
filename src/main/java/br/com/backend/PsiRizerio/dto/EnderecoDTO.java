package br.com.backend.PsiRizerio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class EnderecoDTO {

    private Integer id;

    @NotBlank
    @Size(min = 8, max = 8)
    private String cep;

    @NotBlank
    private String logradouro;

    @NotBlank
    @JsonProperty("bairro")
    private String bairro;

    @NotNull
    private Integer numero;

    @NotBlank
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 2)
    private String uf;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EnderecoDTO(Integer id, String cep, String logradouro, String bairro, Integer numero, String cidade, String uf, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public EnderecoDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 8, max = 8) String getCep() {
        return cep;
    }

    public void setCep(@NotBlank @Size(min = 8, max = 8) String cep) {
        this.cep = cep;
    }

    public @NotBlank String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(@NotBlank String logradouro) {
        this.logradouro = logradouro;
    }

    public @NotBlank String getBairro() {
        return bairro;
    }

    public void setBairro(@NotBlank String bairro) {
        this.bairro = bairro;
    }

    public @NotNull Integer getNumero() {
        return numero;
    }

    public void setNumero(@NotNull Integer numero) {
        this.numero = numero;
    }

    public @NotBlank String getCidade() {
        return cidade;
    }

    public void setCidade(@NotBlank String cidade) {
        this.cidade = cidade;
    }

    public @NotBlank @Size(min = 2, max = 2) String getUf() {
        return uf;
    }

    public void setUf(@NotBlank @Size(min = 2, max = 2) String uf) {
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
