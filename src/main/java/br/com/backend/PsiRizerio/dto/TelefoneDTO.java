package br.com.backend.PsiRizerio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelefoneDTO {

        @JsonProperty("id")
        private Integer id;

        @JsonProperty("ddd")
        private String ddd;

        @JsonProperty("numero")
        private String numero;

        @JsonProperty("tipo")
        private String tipo;

        @JsonProperty("createdAt")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;

        @JsonProperty("fk_cliente")
        private Long fk_cliente;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getDdd() {
                return ddd;
        }

        public void setDdd(String ddd) {
                this.ddd = ddd;
        }

        public String getNumero() {
                return numero;
        }

        public void setNumero(String numero) {
                this.numero = numero;
        }

        public String getTipo() {
                return tipo;
        }

        public void setTipo(String tipo) {
                this.tipo = tipo;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }

        public Long getFk_cliente() {
                return fk_cliente;
        }

        public void setFk_cliente(Long fk_cliente) {
                this.fk_cliente = fk_cliente;
        }
}
