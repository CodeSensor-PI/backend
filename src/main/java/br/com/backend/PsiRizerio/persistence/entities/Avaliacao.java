package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avaliacao_seq")
    @SequenceGenerator(name = "avaliacao_seq", sequenceName = "AVALIACAO_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "nota", columnDefinition = "INT", nullable = false)
    private Integer nota;

    @Column(name = "feedback", columnDefinition = "longtext", nullable = false)
    private String comentario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}


