package br.com.backend.PsiRizerio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("nome")
    private String nome;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas números e ter 11 dígitos")
    @JsonProperty("cpf")
    private String cpf;

    @Email(message = "Email inválido")
    @JsonProperty("email")
    private String email;

    @JsonProperty("senha")
    private String senha;

    @JsonProperty("fkPlano")
    private PlanoDTO fkPlano;

    @JsonProperty("fkEndereco")
    private EnderecoDTO fkEndereco;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas números e ter 11 dígitos") String getCpf() {
        return cpf;
    }

    public void setCpf(@Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas números e ter 11 dígitos") String cpf) {
        this.cpf = cpf;
    }

    public @Email(message = "Email inválido") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email inválido") String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PlanoDTO getFkPlano() {
        return fkPlano;
    }

    public void setFkPlano(PlanoDTO fkPlano) {
        this.fkPlano = fkPlano;
    }

    public EnderecoDTO getFkEndereco() {
        return fkEndereco;
    }

    public void setFkEndereco(EnderecoDTO fkEndereco) {
        this.fkEndereco = fkEndereco;
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
