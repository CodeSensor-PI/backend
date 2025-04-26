package br.com.backend.PsiRizerio.dto.pacienteDTO;

import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteCreateDTO {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    private StatusUsuario status = StatusUsuario.ATIVO;

    @NotBlank
    private String senha;
}
