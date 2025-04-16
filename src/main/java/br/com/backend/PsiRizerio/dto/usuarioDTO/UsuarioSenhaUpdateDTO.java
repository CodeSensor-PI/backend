package br.com.backend.PsiRizerio.dto.usuarioDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSenhaUpdateDTO {
    @NotBlank
    private String senha;

    @NotBlank
    private String novaSenha;
}