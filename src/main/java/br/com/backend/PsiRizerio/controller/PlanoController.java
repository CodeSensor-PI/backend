package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.planoDTO.PlanoCreateDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoUpdateDTO;
import br.com.backend.PsiRizerio.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoController {
    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @PostMapping
    @Operation(summary = "Cria um plano", description = "Cria um plano")
    public ResponseEntity<PlanoCreateDTO> create(@Valid @RequestBody PlanoCreateDTO planoCreateDTO) {
        PlanoCreateDTO plano = planoService.createPlano(planoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(plano);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um plano", description = "Atualiza um plano")
    public ResponseEntity<PlanoUpdateDTO> update(@Valid @RequestBody PlanoUpdateDTO planoUpdateDTO,
                                           @PathVariable Integer id) {
        PlanoUpdateDTO plano = planoService.update(id, planoUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(plano);
    }

    @GetMapping
    @Operation(summary = "Busca todos os planos", description = "Busca todos os planos")
    public ResponseEntity<List<PlanoResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(planoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um plano por ID", description = "Busca um plano por ID")
    public ResponseEntity<PlanoResponseDTO> findById(@PathVariable Integer id) {
        PlanoResponseDTO plano = planoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(plano);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um plano", description = "Deleta um plano")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        planoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
