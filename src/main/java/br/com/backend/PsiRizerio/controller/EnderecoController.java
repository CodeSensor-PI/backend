package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoCreateDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoUpdateDTO;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.service.EnderecoService;
import br.com.backend.PsiRizerio.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "CRUD de endereços e busca personalizada por CEP e número")
public class EnderecoController {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;

    @Operation(summary = "Criar endereço", description = "Cria um novo endereço no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> create(@Valid @RequestBody EnderecoCreateDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        Endereco enderecoToSave = enderecoService.createEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoMapper.toDtoResponse(enderecoToSave));
    }

    @Operation(summary = "Listar todos os endereços", description = "Retorna todos os endereços cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        List<Endereco> enderecos = enderecoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(enderecoMapper.toDtoList(enderecos));
    }

    @Operation(summary = "Buscar endereço por ID", description = "Retorna os dados de um endereço pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> findById(
            @Parameter(description = "ID do endereço") @PathVariable Integer id) {
        Endereco endereco = enderecoService.findById(id);
        return ResponseEntity.ok(enderecoMapper.toDtoResponse(endereco));
    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza os dados de um endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> update(
            @Parameter(description = "ID do endereço") @PathVariable Integer id,
            @Valid @RequestBody EnderecoUpdateDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        Endereco enderecoUpdated = enderecoService.update(id, endereco);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoMapper.toDtoResponse(enderecoUpdated));
    }

    @Operation(summary = "Excluir endereço", description = "Remove um endereço do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do endereço") @PathVariable Integer id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar endereço por CEP e número", description = "Busca um endereço pelo CEP e número do logradouro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @GetMapping("/encontrarEndereco")
    public ResponseEntity<EnderecoResponseDTO> findByCepAndNumero(
            @Parameter(description = "CEP do endereço") @RequestParam String cep,
            @Parameter(description = "Número do endereço") @RequestParam String numero) {
        Endereco enderecoEncontrado = enderecoService.findByCepAndNumero(cep, numero);
        return ResponseEntity.ok(enderecoMapper.toDtoResponse(enderecoEncontrado));
    }
}
