package br.com.backend.PsiRizerio.dto.sessaoDTO;

import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioResponseDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioSessaoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.enums.TipoSessao;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class SessaoUpdateDTO {

    private Integer id;

    private UsuarioSessaoResponseDTO fkCliente;

    @Future
    private LocalDateTime dtHrSessao;

    private TipoSessao tipo;

    private StatusSessao statusSessao;

    private String anotacao;

    private LocalDateTime updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioSessaoResponseDTO getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(UsuarioSessaoResponseDTO fkCliente) {
        this.fkCliente = fkCliente;
    }

    public LocalDateTime getDtHrSessao() {
        return dtHrSessao;
    }

    public void setDtHrSessao(LocalDateTime dtHrSessao) {
        this.dtHrSessao = dtHrSessao;
    }

    public TipoSessao getTipo() {
        return tipo;
    }

    public void setTipo(TipoSessao tipo) {
        this.tipo = tipo;
    }

    public StatusSessao getStatusSessao() {
        return statusSessao;
    }

    public void setStatusSessao(StatusSessao statusSessao) {
        this.statusSessao = statusSessao;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
