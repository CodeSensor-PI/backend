package br.com.backend.PsiRizerio.dto.sessaoDTO;

import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioResponseDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioSessaoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.enums.TipoSessao;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoCreateDTO {

    private Integer id;

    @NotNull
    private UsuarioSessaoResponseDTO fkCliente;

    @NotNull
    private LocalDate data;

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
