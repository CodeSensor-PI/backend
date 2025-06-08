package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneCreateDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneResponseDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneUpdateDTO;
import br.com.backend.PsiRizerio.mapper.TelefoneMapper;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import br.com.backend.PsiRizerio.service.TelefoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/telefones")
@Tag(name = "Telefones", description = "Gerenciamento de telefones dos pacientes")
public class TelefoneController {

    private final TelefoneService telefoneService;
    private final TelefoneMapper telefoneMappper;

    @Operation(summary = "Criar telefone", description = "Cria um novo telefone para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Telefone criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<TelefoneResponseDTO> createTelefone(@Valid @RequestBody TelefoneCreateDTO telefoneCreateDTO) {
        Telefone telefone = telefoneMappper.toEntity(telefoneCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(telefoneMappper.toDtoResponse(telefoneService.createTelefone(telefone)));
    }

    @Operation(summary = "Listar todos os telefones", description = "Retorna uma lista com todos os telefones cadastrados")
    @GetMapping
    public ResponseEntity<List<TelefoneResponseDTO>> findAll() {
        List<Telefone> telefones = telefoneService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(telefoneMappper.toDtoList(telefones));
    }

    @Operation(summary = "Buscar telefone por ID", description = "Busca um telefone específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefone encontrado"),
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TelefoneResponseDTO> findById(
            @Parameter(description = "ID do telefone") @PathVariable Integer id) {
        Telefone telefone = telefoneService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(telefoneMappper.toDtoResponse(telefone));
    }

    @Operation(summary = "Buscar telefones por ID do paciente", description = "Retorna todos os telefones associados a um paciente específico")
    @GetMapping("/pacientes/{id}")
    public ResponseEntity<List<TelefoneResponseDTO>> findByPacienteId(
            @Parameter(description = "ID do paciente") @PathVariable Integer id) {
        List<Telefone> telefones = telefoneService.findByFkPaciente(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(telefoneMappper.toDtoList(telefones));
    }

    @Operation(summary = "Atualizar telefone", description = "Atualiza os dados de um telefone existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TelefoneResponseDTO> update(
            @Parameter(description = "ID do telefone a ser atualizado") @PathVariable Integer id,
            @Valid @RequestBody TelefoneUpdateDTO telefoneUpdateDTO) {
        Telefone telefone = telefoneMappper.toEntity(telefoneUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(telefoneMappper.toDtoResponse(telefoneService.update(id, telefone)));
    }

    @Operation(summary = "Deletar telefone", description = "Remove um telefone pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Telefone deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do telefone a ser deletado") @PathVariable Integer id) {
        telefoneService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
