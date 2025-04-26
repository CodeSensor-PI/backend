package br.com.backend.PsiRizerio.dto.pacienteDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteTokenDTO {

  private Long id;
  private String nome;
  private String email;
  private String token;

}
