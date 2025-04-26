package br.com.backend.PsiRizerio.dto.telefoneDTO;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteSessaoResponseDTO;
import br.com.backend.PsiRizerio.enums.TipoTelefone;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneCreateDTO {

    @NotBlank
    @Min(value = 1)
    private String ddd;

    @NotBlank
    @Min(value = 9)
    private String numero;

    @NotBlank
    private String nomeContato;

    @NotNull
    private TipoTelefone tipo;

    private LocalDateTime createdAt;

    @NotNull
    private PacienteSessaoResponseDTO fkPaciente;

}
