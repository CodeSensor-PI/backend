package br.com.backend.PsiRizerio.dto.pacienteDTO;

import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteResponseDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDate dataNasc;
    private StatusUsuario status;
    private String motivoConsulta;
    private String imagemUrl; // URL da imagem no S3
    private PlanoResponseDTO fkPlano;
    private EnderecoResponseDTO fkEndereco;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}