package br.com.backend.PsiRizerio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanoDTO {

        @JsonProperty("id")
        private Integer id;

        @JsonProperty("categoria")
        private String categoria;

        @JsonProperty("nome")
        private Double preco;

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
