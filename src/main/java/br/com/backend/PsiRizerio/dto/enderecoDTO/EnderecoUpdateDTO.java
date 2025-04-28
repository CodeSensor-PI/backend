package br.com.backend.PsiRizerio.dto.enderecoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class EnderecoUpdateDTO {

    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}")
    private String cep;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String bairro;

    @NotBlank
    @Size(min = 1, max = 5)
    private String numero;

    @NotBlank
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 2)
    private String uf;

    private LocalDateTime updatedAt;
}
