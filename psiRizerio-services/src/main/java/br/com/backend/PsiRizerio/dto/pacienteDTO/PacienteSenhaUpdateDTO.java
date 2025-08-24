package br.com.backend.PsiRizerio.dto.pacienteDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteSenhaUpdateDTO {
    @NotBlank
    private String senha;

    @NotBlank
    private String novaSenha;
}