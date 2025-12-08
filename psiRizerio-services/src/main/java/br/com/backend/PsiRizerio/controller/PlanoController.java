package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.planoDTO.PlanoCreateDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoUpdateDTO;
import br.com.backend.PsiRizerio.mapper.PlanoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
import br.com.backend.PsiRizerio.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/planos")
@Tag(name = "Planos", description = "Gerenciamento dos planos de atendimento")
public class PlanoController {

    private final PlanoService planoService;
    private final PlanoMapper planoMapper;

    @Operation(summary = "Criar plano", description = "Cria um novo plano de atendimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plano criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PlanoResponseDTO> create(
            @Valid @RequestBody PlanoCreateDTO planoCreateDTO) {
        Plano plano = planoMapper.toEntity(planoCreateDTO);
        Plano planoCreated = planoService.createPlano(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(planoMapper.toDtoResponse(planoCreated));
    }

    @Operation(summary = "Atualizar plano", description = "Atualiza os dados de um plano existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> update(
            @Valid @RequestBody PlanoUpdateDTO planoUpdateDTO,
            @Parameter(description = "ID do plano a ser atualizado") @PathVariable Integer id) {
        Plano plano = planoMapper.toEntity(planoUpdateDTO);
        Plano planoUpdated = planoService.update(id, plano);
        return ResponseEntity.status(HttpStatus.OK).body(planoMapper.toDtoResponse(planoUpdated));
    }

    @Operation(summary = "Listar todos os planos", description = "Retorna todos os planos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de planos retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<PlanoResponseDTO>> findAll() {
        List<Plano> planos = planoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(planoMapper.toDtoList(planos));
    }

    @Operation(summary = "Buscar plano por ID", description = "Retorna os dados de um plano específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano encontrado"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> findById(
            @Parameter(description = "ID do plano") @PathVariable Integer id) {
        Plano plano = planoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(planoMapper.toDtoResponse(plano));
    }

    @Operation(summary = "Deletar plano", description = "Remove um plano do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plano deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do plano a ser deletado") @PathVariable Integer id) {
        planoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
