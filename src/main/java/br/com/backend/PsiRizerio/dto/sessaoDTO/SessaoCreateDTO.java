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
public class SessaoCreateDTO {

    private Integer id;

    @NotNull
    private PacienteSessaoResponseDTO fkPaciente;

    @Future
    @NotNull
    private LocalDate data;

    @Future
    @NotNull
    private LocalTime hora;

    @NotNull
    private TipoSessao tipo;

    @NotNull
    private StatusSessao statusSessao = StatusSessao.AGUARDANDO;

    @NotBlank
    private String anotacao;
    private LocalDateTime createdAt;

}
