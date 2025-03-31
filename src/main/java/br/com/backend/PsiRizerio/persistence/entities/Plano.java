package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plano_seq")
    @SequenceGenerator(name = "plano_seq", sequenceName = "PLANO_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "categoria", columnDefinition = "VARCHAR(25)")
    private String categoria;

    @Column(name = "preco", columnDefinition = "DECIMAL(6,2)")
    private Double preco;

    public Plano(Integer id, String categoria, Double preco) {
        this.id = id;
        this.categoria = categoria;
        this.preco = preco;
    }

    public Plano() {
    }

    public Plano(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
