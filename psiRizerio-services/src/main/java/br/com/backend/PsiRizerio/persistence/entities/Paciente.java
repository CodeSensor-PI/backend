package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.StatusUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "paciente")
public class Paciente {
        //colocar fkPsicologo

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "nome", columnDefinition = "VARCHAR(60)")
        private String nome;

        @Column(name = "cpf", columnDefinition = "CHAR(11)", unique = true)
        private String cpf;

        @Column(name = "motivo_consulta", columnDefinition = "TEXT")
        private String motivoConsulta;

        @Column(name = "email", columnDefinition = "VARCHAR(80)", unique = true)
        private String email;

        @Column(name = "imagem_url", columnDefinition = "TEXT")
        private String imagemUrl;

        @Column(name = "senha", columnDefinition = "VARCHAR(100)")
        private String senha;

        @Column(name = "dat_nasc", columnDefinition = "DATE")
        private LocalDate dataNasc;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "VARCHAR(20)")
        private StatusUsuario status;

        @ManyToOne
        @JoinColumn(name = "fk_plano", referencedColumnName = "id")
        private Plano fkPlano;

        @ManyToOne
        @JoinColumn(name = "fk_endereco", referencedColumnName = "id")
        private Endereco fkEndereco;

        @CreatedDate
        @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
        private LocalDateTime createdAt;

        @CreatedDate
        @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
        private LocalDateTime updatedAt;
}
