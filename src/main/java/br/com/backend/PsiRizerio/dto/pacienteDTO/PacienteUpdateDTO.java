package br.com.backend.PsiRizerio.dto.pacienteDTO;

import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaResponseDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteUpdateDTO {

    private PlanoResponseDTO fkPlano;

    private StatusUsuario status = StatusUsuario.ATIVO;

    @NotBlank
    private String nome;

    @CPF
    @NotBlank
    private String cpf;

    @Email
    @NotBlank
    private String email;

    private EnderecoResponseDTO fkEndereco;

}
