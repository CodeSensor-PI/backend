package br.com.backend.PsiRizerio.dto.usuarioDTO;

import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

public class UsuarioCreateDTO {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    private StatusUsuario status = StatusUsuario.ATIVO;

    @NotBlank
    private String senha;

    private PlanoResponseDTO fkPlano;


}
