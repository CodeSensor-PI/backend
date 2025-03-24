package br.com.backend.PsiRizerio.dto.userDTO;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.dto.PlanoDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

public class UsuarioUpdateDTO {

    private Integer id;

    private String nome;

    @CPF
    private String cpf;

    @Email
    private String email;

    private String senha;

    private PlanoDTO fkPlano;

    private EnderecoDTO fkEndereco;

    @NotNull
    private LocalDateTime updatedAt = LocalDateTime.now();

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

    public @CPF String getCpf() {
        return cpf;
    }

    public void setCpf(@CPF String cpf) {
        this.cpf = cpf;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
