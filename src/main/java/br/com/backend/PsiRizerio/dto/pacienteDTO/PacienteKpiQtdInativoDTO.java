package br.com.backend.PsiRizerio.dto.pacienteDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteKpiQtdInativoDTO {
    private Double porcentPacienteInativo;
}
