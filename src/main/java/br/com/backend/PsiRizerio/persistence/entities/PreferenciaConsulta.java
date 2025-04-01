package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "preferencia_consulta")
public class PreferenciaConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preferencia_consulta_seq")
    @SequenceGenerator(name = "preferencia_consulta_seq", sequenceName = "PREFERENCIA_CONSULTA_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "frequencia", columnDefinition = "VARCHAR(10)")
    private String frequencia;

    @Column(name = "dia_semana", columnDefinition = "VARCHAR(10)")
    private String diaSemana;

    @Column(name = "horario", columnDefinition = "VARCHAR(5)")
    private String horario;

    @Column(name = "plataforma_atendimento" , columnDefinition = "VARCHAR(15)")
    private String plataformaAtendimento;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
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
