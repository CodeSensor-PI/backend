package br.com.backend.PsiRizerio.dto.psicologoDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsicologoSenhaUpdateDTO {
    @NotBlank
    private String senha;

    @NotBlank
    private String novaSenha;
}
