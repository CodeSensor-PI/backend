package br.com.backend.PsiRizerio.dto.planoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanoResponseDTO {

    private Integer id;

    @NotBlank
    private String categoria;
    @NotNull
    private Double preco;

}
