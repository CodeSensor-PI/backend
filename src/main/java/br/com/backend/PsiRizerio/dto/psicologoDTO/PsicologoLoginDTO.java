package br.com.backend.PsiRizerio.dto.psicologoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsicologoLoginDTO {

    private String email;
    private String senha;
}
