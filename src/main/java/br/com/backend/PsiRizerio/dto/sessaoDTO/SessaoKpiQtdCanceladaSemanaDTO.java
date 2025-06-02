package br.com.backend.PsiRizerio.dto.sessaoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoKpiQtdCanceladaSemanaDTO {
    private Double qtdCancelada;
}
