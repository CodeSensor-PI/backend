package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoCreateDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoUpdateDTO;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.service.EnderecoService;
import br.com.backend.PsiRizerio.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
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
public class EnderecoController {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;

    @PostMapping
    @Operation(summary = "Cria um endereço", description = "Cria um endereço")
    public ResponseEntity<EnderecoResponseDTO> create(@Valid @RequestBody EnderecoCreateDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        Endereco enderecoToSave = enderecoService.createEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoMapper.toDtoResponse(enderecoToSave));
    }

    @GetMapping
    @Operation(summary = "Busca todos os endereços", description = "Busca todos os endereços")
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        List<Endereco> enderecos = enderecoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(enderecoMapper.toDtoList(enderecos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um endereço por ID", description = "Busca um endereço por ID")
    public ResponseEntity<EnderecoResponseDTO> findById(@PathVariable Integer id) {
        Endereco endereco = enderecoService.findById(id);
        return ResponseEntity.ok(enderecoMapper.toDtoResponse(endereco));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um endereço", description = "Atualiza um endereço")
    public ResponseEntity<EnderecoResponseDTO> update(@PathVariable Integer id,
                                                      @Valid @RequestBody EnderecoUpdateDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        Endereco enderecoUpdated = enderecoService.update(id, endereco);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoMapper.toDtoResponse(enderecoUpdated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um endereço", description = "Deleta um endereço")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/encontrarEndereco")
    @Operation(summary = "Busca um endereco por CEP e número do logradouro", description = "Busca um endereco por CEP e número do logradouro")
    public ResponseEntity<EnderecoResponseDTO> findByCepAndNumero(@RequestParam String cep, @RequestParam String numero) {
        Endereco enderecoEncontrado = enderecoService.findByCepAndNumero(cep, numero);
        return ResponseEntity.ok(enderecoMapper.toDtoResponse(enderecoEncontrado));
    }


}
