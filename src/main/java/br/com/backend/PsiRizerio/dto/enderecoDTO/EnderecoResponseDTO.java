package br.com.backend.PsiRizerio.dto.enderecoDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class EnderecoResponseDTO {

    private Integer id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String numero;
    private String cidade;
    private String uf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
