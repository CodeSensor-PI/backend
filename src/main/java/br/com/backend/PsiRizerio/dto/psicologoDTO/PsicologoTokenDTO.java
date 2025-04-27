package br.com.backend.PsiRizerio.dto.psicologoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PsicologoTokenDTO {

    private Long id;
    private String nome;
    private String email;
    private String token;

}
