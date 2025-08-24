package br.com.backend.PsiRizerio.dto.enderecoDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@Schema(description = "DTO para criação de um novo endereço")
public class EnderecoCreateDTO {

    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}")
    @Schema(description = "CEP do endereço, somente números", example = "01001000")
    private String cep;

    @NotBlank
    @Schema(description = "Nome da rua ou logradouro", example = "Avenida Paulista")
    private String logradouro;

    @NotBlank
    @Schema(description = "Bairro do endereço", example = "Bela Vista")
    private String bairro;

    @NotBlank
    @Size(min = 1, max = 5)
    @Schema(description = "Número da residência", example = "123")
    private String numero;

    @NotBlank
    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 2)
    @Schema(description = "Unidade federativa (UF), sigla do estado", example = "SP")
    private String uf;

    @Schema(description = "Data de criação do registro", example = "2024-06-08T14:23:00")
    private LocalDateTime createdAt;
}
