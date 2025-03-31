package br.com.backend.PsiRizerio.dto.telefoneDTO;

import br.com.backend.PsiRizerio.enums.TipoTelefone;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class TelefoneRespondeDTO {

    private Integer id;
    private String ddd;
    private String numero;
    private TipoTelefone tipo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Usuario fkUsuario;

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

    public Usuario getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Usuario fkUsuario) {
        this.fkUsuario = fkUsuario;
    }
}
