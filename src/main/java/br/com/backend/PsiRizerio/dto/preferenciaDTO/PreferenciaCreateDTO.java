package br.com.backend.PsiRizerio.dto.preferenciaDTO;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteResponseDTO;
import br.com.backend.PsiRizerio.enums.DiaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciaCreateDTO {
    @NotNull
    private DiaSemana diaSemana;

    @NotBlank
    private String horario;

    private PacienteResponseDTO fkPaciente;

    private LocalDateTime createdAt;
}
