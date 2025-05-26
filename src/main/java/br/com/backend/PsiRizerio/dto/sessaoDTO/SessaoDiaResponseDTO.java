package br.com.backend.PsiRizerio.dto.sessaoDTO;


import br.com.backend.PsiRizerio.enums.StatusSessao;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SessaoDiaResponseDTO {
    private Integer idSessao;
    private Integer idPaciente;
    private String nomePaciente;
    private LocalDate data;
    private LocalTime hora;
    private StatusSessao status;
}
