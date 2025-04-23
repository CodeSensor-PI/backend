package br.com.backend.PsiRizerio.dto.pacienteDTO;

import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;

import java.time.LocalDateTime;

public class PacienteResponseDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private StatusUsuario status;
    private PlanoResponseDTO fkPlano;
    private EnderecoResponseDTO fkEndereco;
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

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}