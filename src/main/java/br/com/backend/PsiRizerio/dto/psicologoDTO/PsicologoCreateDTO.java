package br.com.backend.PsiRizerio.dto.psicologoDTO;

import br.com.backend.PsiRizerio.persistence.entities.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PsicologoCreateDTO {

    private Integer id;
    @NotBlank
    private String nome;
    @NotBlank
    private String crp;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private String telefone;
    private Roles fkRoles;

}
