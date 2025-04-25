package br.com.backend.PsiRizerio.dto.pacienteDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteLoginDTO {

  @Schema(description = "E-mail do paciente", example = "john@doe.com")
  private String email;
  @Schema(description = "Senha do paciente", example = "123456")
  private String senha;

}
