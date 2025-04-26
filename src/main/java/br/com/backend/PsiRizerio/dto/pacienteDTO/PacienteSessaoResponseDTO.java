package br.com.backend.PsiRizerio.dto.pacienteDTO;

import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteSessaoResponseDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private StatusUsuario status;
    private PlanoResponseDTO fkPlano;

}
