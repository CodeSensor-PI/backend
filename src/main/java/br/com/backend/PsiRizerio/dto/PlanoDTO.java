package br.com.backend.PsiRizerio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class PlanoDTO {

        @JsonProperty("id")
        private Integer id;

        @NotBlank
        private String categoria;

        @NotNull
        private Double preco;

        public PlanoDTO(Integer id, String categoria, Double preco) {
                this.id = id;
                this.categoria = categoria;
                this.preco = preco;
        }

        public PlanoDTO() {
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
