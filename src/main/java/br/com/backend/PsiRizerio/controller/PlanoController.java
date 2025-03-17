package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.PlanoDTO;
import br.com.backend.PsiRizerio.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<PlanoDTO> create(PlanoDTO planoDTO) {
        PlanoDTO plano = planoService.createPlano(planoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(plano);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um plano", description = "Atualiza um plano")
    public ResponseEntity<PlanoDTO> update(@RequestBody PlanoDTO planoDTO,
                                           @PathVariable Integer id) {
        PlanoDTO plano = planoService.update(id, planoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(plano);
    }

    @GetMapping
    @Operation(summary = "Busca todos os planos", description = "Busca todos os planos")
    public ResponseEntity<List<PlanoDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(planoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um plano por ID", description = "Busca um plano por ID")
    public ResponseEntity<PlanoDTO> findById(@PathVariable Integer id) {
        PlanoDTO plano = planoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(plano);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um plano", description = "Deleta um plano")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        planoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
