package br.com.backend.PsiRizerio.dto.avaliacaoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoUpdateDTO {

    @NotNull
    private Integer nota;

    @NotBlank
    private String feedback;
}
