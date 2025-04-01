package br.com.backend.PsiRizerio.dto.usuarioDTO;

import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    private StatusUsuario status = StatusUsuario.ATIVO;

    @NotBlank
    private String senha;

    private PlanoResponseDTO fkPlano;

    private EnderecoResponseDTO fkEndereco;

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

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
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

    public PlanoResponseDTO getFkPlano() {
        return fkPlano;
    }

    public void setFkPlano(PlanoResponseDTO fkPlano) {
        this.fkPlano = fkPlano;
    }

    public EnderecoResponseDTO getFkEndereco() {
        return fkEndereco;
    }

    public void setFkEndereco(EnderecoResponseDTO fkEndereco) {
        this.fkEndereco = fkEndereco;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
