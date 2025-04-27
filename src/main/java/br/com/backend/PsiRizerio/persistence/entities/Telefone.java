package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.TipoTelefone;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telefone_seq")
    @SequenceGenerator(name = "telefone_seq", sequenceName = "TELEFONE_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "ddd", columnDefinition = "CHAR(2)")
    private String ddd;

    @Column(name = "numero", columnDefinition = "CHAR(14)")
    private String numero;

    @Column(name = "tipo", columnDefinition = "VARCHAR(15)")
    @Enumerated(EnumType.STRING)
    private TipoTelefone tipo;

    @Column(name = "nome_contato", columnDefinition = "VARCHAR(45)")
    private String nomeContato;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "fk_paciente", referencedColumnName = "id")
    private Paciente fkPaciente;

}
