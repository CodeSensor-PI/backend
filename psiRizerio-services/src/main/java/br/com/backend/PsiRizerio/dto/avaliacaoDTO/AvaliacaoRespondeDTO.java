package br.com.backend.PsiRizerio.dto.avaliacaoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoRespondeDTO {
    private Integer id;
    private Integer nota;
    private String feedback;
}