package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "categoria", columnDefinition = "VARCHAR(25)")
    private String categoria;

    @Column(name = "preco", columnDefinition = "DECIMAL(6,2)")
    private Double preco;
}
