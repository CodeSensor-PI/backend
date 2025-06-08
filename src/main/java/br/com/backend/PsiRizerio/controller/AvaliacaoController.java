package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoRespondeDTO;
import br.com.backend.PsiRizerio.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avaliacoes")
@Tag(name = "Avaliações", description = "CRUD de avaliações realizadas pelos pacientes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @Operation(summary = "Criar nova avaliação", description = "Cria uma nova avaliação com base nos dados informados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da avaliação inválidos")
    })
    @PostMapping
    public ResponseEntity<AvaliacaoRespondeDTO> createAvaliacao(
            @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) {
        AvaliacaoRespondeDTO createdAvaliacao = avaliacaoService.createAvaliacao(avaliacaoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAvaliacao);
    }

    @Operation(summary = "Listar todas as avaliações", description = "Retorna a lista de todas as avaliações registradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<AvaliacaoRespondeDTO>> getAllAvaliacoes() {
        List<AvaliacaoRespondeDTO> avaliacoes = avaliacaoService.getAllAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    @Operation(summary = "Excluir avaliação", description = "Remove uma avaliação existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(
            @Parameter(description = "ID da avaliação a ser removida") @PathVariable Integer id) {
        avaliacaoService.deleteAvaliacaoById(id);
        return ResponseEntity.noContent().build();
    }
}
