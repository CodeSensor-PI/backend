package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaResponseDTO;
import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaCreateDTO;
import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaUpdateDTO;
import br.com.backend.PsiRizerio.mapper.PreferenciaMapper;
import br.com.backend.PsiRizerio.persistence.entities.Preferencia;
import br.com.backend.PsiRizerio.service.PreferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preferencias")
@Tag(name = "Preferências", description = "Gerenciamento das preferências dos pacientes")
public class PreferenciaController {

    private final PreferenciaService preferenciaService;
    private final PreferenciaMapper preferenciaMapper;

    @Operation(summary = "Criar preferência", description = "Cria uma nova preferência no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Preferência criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PreferenciaResponseDTO> createPreferencia(
            @Valid @RequestBody PreferenciaCreateDTO preferenciaCreateDTO) {
        Preferencia preferencia = preferenciaMapper.toEntity(preferenciaCreateDTO);
        return ResponseEntity.status(201)
                .body(preferenciaMapper.toDtoResponse(preferenciaService.createPreferencia(preferencia)));
    }

    @Operation(summary = "Listar preferências", description = "Retorna todas as preferências cadastradas")
    @GetMapping
    public ResponseEntity<List<PreferenciaResponseDTO>> findPreferencia() {
        List<Preferencia> preferencias = preferenciaService.findAllPreferencias();
        return ResponseEntity.status(200)
                .body(preferenciaMapper.toDtoList(preferencias));
    }

    @Operation(summary = "Buscar preferência por ID do Paciente", description = "Retorna uma preferência específica pelo ID do Paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferência encontrada"),
            @ApiResponse(responseCode = "404", description = "Preferência não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PreferenciaResponseDTO> findPreferenciaById(
            @Parameter(description = "ID do Paciente") @PathVariable Integer id) {
        Preferencia preferencia = preferenciaService.findPreferenciaById(id);
        return ResponseEntity.status(200)
                .body(preferenciaMapper.toDtoResponse(preferencia));
    }

    @Operation(summary = "Atualizar preferência", description = "Atualiza os dados de uma preferência existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferência atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Preferência não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PreferenciaResponseDTO> updatePreferencia(
            @Parameter(description = "ID da preferência a ser atualizada") @PathVariable Integer id,
            @Valid @RequestBody PreferenciaUpdateDTO preferenciaUpdateDTO) {
        Preferencia preferencia = preferenciaMapper.toEntity(preferenciaUpdateDTO);
        return ResponseEntity.status(200)
                .body(preferenciaMapper.toDtoResponse(preferenciaService.updatePreferencia(id, preferencia)));
    }

    @Operation(summary = "Deletar preferência", description = "Remove uma preferência do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Preferência deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Preferência não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreferencia(
            @Parameter(description = "ID da preferência a ser deletada") @PathVariable Integer id) {
        preferenciaService.deletePreferencia(id);
        return ResponseEntity.status(204).build();
    }
}
