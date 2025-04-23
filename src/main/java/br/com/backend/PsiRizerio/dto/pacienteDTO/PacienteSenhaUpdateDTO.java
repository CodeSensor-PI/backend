package br.com.backend.PsiRizerio.dto.pacienteDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteSenhaUpdateDTO {
    @NotBlank
    private String senha;

    @NotBlank
    private String novaSenha;
}