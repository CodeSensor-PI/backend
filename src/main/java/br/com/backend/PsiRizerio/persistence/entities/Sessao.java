package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.enums.TipoSessao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessao")
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_cliente", columnDefinition = "INT")
    private Usuario fkCliente;

    @Column(name = "data", columnDefinition = "DATE")
    private LocalDate data;

    @Column(name = "hora", columnDefinition = "TIMESTAMP")
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", columnDefinition = "VARCHAR(10)")
    private TipoSessao tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_sessao", columnDefinition = "VARCHAR(15)")
    private StatusSessao statusSessao;

    @Column(name = "anotacao", columnDefinition = "LONGTEXT")
    private String anotacao;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
