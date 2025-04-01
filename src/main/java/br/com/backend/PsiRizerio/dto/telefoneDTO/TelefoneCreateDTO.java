package br.com.backend.PsiRizerio.dto.telefoneDTO;

import br.com.backend.PsiRizerio.enums.TipoTelefone;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TelefoneCreateDTO {

    @NotBlank
    private String ddd;

    @NotBlank
    private String numero;

    @NotNull
    private TipoTelefone tipo;

    private LocalDateTime createdAt;

    @NotNull
    private Usuario fkUsuario;

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

    public Usuario getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Usuario fkUsuario) {
        this.fkUsuario = fkUsuario;
    }
}
