package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avaliacao_seq")
    @SequenceGenerator(name = "avaliacao_seq", sequenceName = "AVALIACAO_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "nota")
    private Integer nota;

    @Column(name = "feedback", columnDefinition = "longtext")
    private String feedback;

}


