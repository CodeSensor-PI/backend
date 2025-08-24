package br.com.backend.PsiRizerio.dto.telefoneDTO;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteSessaoResponseDTO;
import br.com.backend.PsiRizerio.enums.TipoTelefone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneUpdateDTO {

    private String ddd;
    private String numero;
    private TipoTelefone tipo;
    private String nomeContato;
    private LocalDateTime updateAt;
    private PacienteSessaoResponseDTO fkPaciente;

}
