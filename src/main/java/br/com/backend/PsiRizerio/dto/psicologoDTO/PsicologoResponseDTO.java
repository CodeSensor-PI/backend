package br.com.backend.PsiRizerio.dto.psicologoDTO;

import br.com.backend.PsiRizerio.persistence.entities.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsicologoResponseDTO {

    private Integer id;
    private String nome;
    private String crp;
    private String email;
    private String telefone;
    private Roles fkRoles;
}
