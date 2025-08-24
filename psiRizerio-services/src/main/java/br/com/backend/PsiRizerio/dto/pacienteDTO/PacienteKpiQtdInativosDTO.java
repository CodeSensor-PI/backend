package br.com.backend.PsiRizerio.dto.pacienteDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteKpiQtdInativosDTO {
    private Double porcentPacienteInativo;
}
