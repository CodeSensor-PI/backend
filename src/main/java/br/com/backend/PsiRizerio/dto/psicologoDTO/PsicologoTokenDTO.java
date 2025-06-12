package br.com.backend.PsiRizerio.dto.psicologoDTO;

import br.com.backend.PsiRizerio.persistence.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PsicologoTokenDTO {

    private Long id;
    private String nome;
    private String email;
    private String token;
    private Roles role;

    public PsicologoTokenDTO(String token) {
    }
}
