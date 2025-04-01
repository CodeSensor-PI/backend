package br.com.backend.PsiRizerio.dto.preferenciaConsultaDTO;

import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class PreferenciaConsultaUpdateDTO {

    @NotBlank
    private String frequencia;

    @NotBlank
    private String diaSemana;

    @NotBlank
    private String horario;

    @NotBlank
    private String plataformaAtendimento;

    private LocalDateTime updatedAt;
    private Usuario fkUser;

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getPlataformaAtendimento() {
        return plataformaAtendimento;
    }

    public void setPlataformaAtendimento(String plataformaAtendimento) {
        this.plataformaAtendimento = plataformaAtendimento;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Usuario getFkUser() {
        return fkUser;
    }

    public void setFkUser(Usuario fkUser) {
        this.fkUser = fkUser;
    }
}
