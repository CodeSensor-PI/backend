package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "psicologos")
public class Psicologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", columnDefinition = "VARCHAR(60)")
    private String nome;

    @Column(name = "crp", columnDefinition = "VARCHAR(7)", unique = true)
    private String crp;

    @Column(name = "email", columnDefinition = "VARCHAR(80)", unique = true)
    private String email;

    @Column(name = "senha", columnDefinition = "VARCHAR(100)")
    private String senha;

    @Column(name = "telefone", columnDefinition = "VARCHAR(15)")
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "roles", columnDefinition = "INT")
    private Roles fkRoles;

}
