package br.com.backend.PsiRizerio.dto.preferenciaDTO;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteResponseDTO;
import br.com.backend.PsiRizerio.enums.DiaSemana;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciaResponseDTO {

    private Integer id;
    private DiaSemana diaSemana;
    private String horario;
    private PacienteResponseDTO fkPaciente;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
