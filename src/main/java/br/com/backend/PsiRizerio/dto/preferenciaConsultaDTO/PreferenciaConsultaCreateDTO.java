package br.com.backend.PsiRizerio.dto.preferenciaConsultaDTO;

import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PreferenciaConsultaCreateDTO {

    @NotBlank
    private String frequencia;

    @NotBlank
    private String diaSemana;

    @NotBlank
    private String horario;

    @NotBlank
    private String plataformaAtendimento;

    private LocalDateTime createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Usuario getFkUser() {
        return fkUser;
    }

    public void setFkUser(Usuario fkUser) {
        this.fkUser = fkUser;
    }
}
