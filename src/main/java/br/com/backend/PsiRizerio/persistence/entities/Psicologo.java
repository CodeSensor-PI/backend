package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.StatusUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    private StatusUsuario status;

    @CreatedDate
    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @CreatedDate
    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
