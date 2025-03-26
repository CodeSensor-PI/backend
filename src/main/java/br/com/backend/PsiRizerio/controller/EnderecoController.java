package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoCreateDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoUpdateDTO;
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
    public ResponseEntity<EnderecoCreateDTO> create(@Valid @RequestBody EnderecoCreateDTO enderecoDTO) {
        EnderecoCreateDTO endereco = enderecoService.createEndereco(enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @GetMapping
    @Operation(summary = "Busca todos os endereços", description = "Busca todos os endereços")
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        List<EnderecoResponseDTO> enderecos = enderecoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(enderecos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um endereço por ID", description = "Busca um endereço por ID")
    public ResponseEntity<EnderecoResponseDTO> findById(@PathVariable Integer id) {
        EnderecoResponseDTO endereco = enderecoService.findById(id);
        return ResponseEntity.ok(endereco);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um endereço", description = "Atualiza um endereço")
    public ResponseEntity<EnderecoUpdateDTO> update(@PathVariable Integer id,
                                                      @Valid @RequestBody EnderecoUpdateDTO enderecoDTO) {
        EnderecoUpdateDTO endereco = enderecoService.update(id, enderecoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um endereço", description = "Deleta um endereço")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
