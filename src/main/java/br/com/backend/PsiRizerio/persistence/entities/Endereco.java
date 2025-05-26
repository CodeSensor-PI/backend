package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cep", columnDefinition = "CHAR(9)")
    private String cep;

    @Column(name = "logradouro", columnDefinition = "VARCHAR(60)")
    private String logradouro;

    @Column(name = "numero", columnDefinition = "VARCHAR(5)")
    private String numero;

    @Column(name = "cidade", columnDefinition = "VARCHAR(25)")
    private String cidade;

    @Column(name = "bairro", columnDefinition = "VARCHAR(80)")
    private String bairro;

    @Column(name = "uf", columnDefinition = "CHAR(2)")
    private String uf;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
