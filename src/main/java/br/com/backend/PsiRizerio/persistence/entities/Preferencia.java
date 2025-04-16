package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "preferencia")
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dia_semana", columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @Column(name = "horario", columnDefinition = "VARCHAR(5)")
    private String horario;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
