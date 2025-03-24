package br.com.backend.PsiRizerio.dto.userDTO;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.dto.PlanoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

public class UsuarioCreateDTO {

    @NotBlank
    private String nome;

    @CPF
    @NotBlank
    private String cpf;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    private PlanoDTO fkPlano;

    private EnderecoDTO fkEndereco;

    private LocalDateTime createdAt;

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @CPF @NotBlank String getCpf() {
        return cpf;
    }

    public void setCpf(@CPF @NotBlank String cpf) {
        this.cpf = cpf;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank String senha) {
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
}
