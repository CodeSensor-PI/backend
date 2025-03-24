package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.service.EnderecoService;
import br.com.backend.PsiRizerio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    @Operation(summary = "Cria um endereço", description = "Cria um endereço")
    public ResponseEntity<EnderecoDTO> create(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO endereco = enderecoService.createEndereco(enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @GetMapping
    @Operation(summary = "Busca todos os endereços", description = "Busca todos os endereços")
    public ResponseEntity<List<EnderecoDTO>> findAll() {
        List<EnderecoDTO> enderecos = enderecoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(enderecos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um endereço por ID", description = "Busca um endereço por ID")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable Integer id) {
        EnderecoDTO endereco = enderecoService.findById(id);
        return ResponseEntity.ok(endereco);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um endereço", description = "Atualiza um endereço")
    public ResponseEntity<EnderecoDTO> update(@PathVariable Integer id, @Valid @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO endereco = enderecoService.update(id, enderecoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um endereço", description = "Deleta um endereço")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
