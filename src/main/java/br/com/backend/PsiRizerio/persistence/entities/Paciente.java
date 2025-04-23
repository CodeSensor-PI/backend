package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.StatusUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cliente")
public class Paciente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "nome", columnDefinition = "VARCHAR(60)")
        private String nome;

        @Column(name = "cpf", columnDefinition = "CHAR(14)", unique = true)
        private String cpf;

        @Column(name = "email", columnDefinition = "VARCHAR(80)", unique = true)
        private String email;

        @Column(name = "senha", columnDefinition = "VARCHAR(100)")
        private String senha;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "VARCHAR(20)")
        private StatusUsuario status;

        @ManyToOne
        @JoinColumn(name = "fk_plano", referencedColumnName = "id")
        private Plano fkPlano;

        @ManyToOne
        @JoinColumn(name = "fk_endereco", referencedColumnName = "id")
        private Endereco fkEndereco;

        @OneToOne
        @JoinColumn(name = "fk_preferencia", referencedColumnName = "id")
        private Preferencia preferencia;

        @CreatedDate
        @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
        private LocalDateTime createdAt;

        @CreatedDate
        @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
        private LocalDateTime updatedAt;
}
