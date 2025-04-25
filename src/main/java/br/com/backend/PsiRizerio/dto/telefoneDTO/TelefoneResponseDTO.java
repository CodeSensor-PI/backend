package br.com.backend.PsiRizerio.dto.telefoneDTO;

import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioSessaoResponseDTO;
import br.com.backend.PsiRizerio.enums.TipoTelefone;

import java.time.LocalDateTime;

public class TelefoneResponseDTO {

    private Integer id;
    private String ddd;
    private String numero;
    private TipoTelefone tipo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UsuarioSessaoResponseDTO fkCliente;

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

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
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

    public UsuarioSessaoResponseDTO getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(UsuarioSessaoResponseDTO fkCliente) {
        this.fkCliente = fkCliente;
    }
}
