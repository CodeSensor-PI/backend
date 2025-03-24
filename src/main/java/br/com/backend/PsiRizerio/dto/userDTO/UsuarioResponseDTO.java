package br.com.backend.PsiRizerio.dto.userDTO;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.dto.PlanoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UsuarioResponseDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private PlanoDTO fkPlano;
    private EnderecoDTO fkEndereco;
    private LocalDateTime createdAt;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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