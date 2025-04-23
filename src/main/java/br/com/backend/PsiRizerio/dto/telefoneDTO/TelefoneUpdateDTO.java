package br.com.backend.PsiRizerio.dto.telefoneDTO;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteSessaoResponseDTO;
import br.com.backend.PsiRizerio.enums.TipoTelefone;

import java.time.LocalDateTime;

public class TelefoneUpdateDTO {

    private String ddd;
    private String numero;
    private TipoTelefone tipo;
    private LocalDateTime updateAt;
    private PacienteSessaoResponseDTO fkCliente;

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

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public PacienteSessaoResponseDTO getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(PacienteSessaoResponseDTO fkCliente) {
        this.fkCliente = fkCliente;
    }
}
