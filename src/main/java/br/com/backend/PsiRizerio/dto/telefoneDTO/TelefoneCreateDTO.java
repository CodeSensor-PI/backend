package br.com.backend.PsiRizerio.dto.telefoneDTO;

import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioSessaoResponseDTO;
import br.com.backend.PsiRizerio.enums.TipoTelefone;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TelefoneCreateDTO {

    @NotBlank
    @Min(value = 1)
    private String ddd;

    @NotBlank
    @Min(value = 9)
    private String numero;

    @NotBlank
    private String nomeContato;

    @NotNull
    private TipoTelefone tipo;

    private LocalDateTime createdAt;

    @NotNull
    private UsuarioSessaoResponseDTO fkCliente;

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

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
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

    public UsuarioSessaoResponseDTO getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(UsuarioSessaoResponseDTO fkCliente) {
        this.fkCliente = fkCliente;
    }
}
