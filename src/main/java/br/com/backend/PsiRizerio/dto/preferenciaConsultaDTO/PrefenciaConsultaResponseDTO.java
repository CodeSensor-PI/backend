package br.com.backend.PsiRizerio.dto.preferenciaConsultaDTO;

import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PrefenciaConsultaResponseDTO {

    private Integer id;
    private String frequencia;
    private String diaSemana;
    private String horario;
    private String plataformaAtendimento;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Usuario fkUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
