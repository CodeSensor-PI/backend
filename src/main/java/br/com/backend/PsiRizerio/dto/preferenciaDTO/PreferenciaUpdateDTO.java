package br.com.backend.PsiRizerio.dto.preferenciaDTO;

import br.com.backend.PsiRizerio.enums.DiaSemana;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciaUpdateDTO {

    private DiaSemana diaSemana;
    private String horario;
    private LocalDateTime updatedAt;

}
