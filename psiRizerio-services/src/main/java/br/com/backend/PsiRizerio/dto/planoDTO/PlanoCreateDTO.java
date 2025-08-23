package br.com.backend.PsiRizerio.dto.planoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlanoCreateDTO {

    @NotBlank
    private String categoria;

    @NotNull
    private Double preco;

    public @NotBlank String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotBlank String categoria) {
        this.categoria = categoria;
    }

    public @NotNull Double getPreco() {
        return preco;
    }

    public void setPreco(@NotNull Double preco) {
        this.preco = preco;
    }
}
