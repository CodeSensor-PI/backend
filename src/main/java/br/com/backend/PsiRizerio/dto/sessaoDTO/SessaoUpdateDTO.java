package br.com.backend.PsiRizerio.dto.sessaoDTO;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteSessaoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.enums.TipoSessao;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoUpdateDTO {

    private Integer id;

    private PacienteSessaoResponseDTO fkPaciente;

    @FutureOrPresent
    private LocalDate data;

    private LocalTime hora;

    private TipoSessao tipo;

    private StatusSessao statusSessao;

    private String anotacao;

    private LocalDateTime updateAt;

}
