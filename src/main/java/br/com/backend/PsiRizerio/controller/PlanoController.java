package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.planoDTO.PlanoCreateDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoUpdateDTO;
import br.com.backend.PsiRizerio.mapper.PlanoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
import br.com.backend.PsiRizerio.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/planos")
public class PlanoController {
    private final PlanoService planoService;
    private final PlanoMapper planoMapper;

    @PostMapping
    @Operation(summary = "Cria um plano", description = "Cria um plano")
    public ResponseEntity<PlanoResponseDTO> create(@Valid @RequestBody PlanoCreateDTO planoCreateDTO) {
        Plano plano = planoMapper.toEntity(planoCreateDTO);
        Plano planoCreated = planoService.createPlano(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(planoMapper.toDtoResponse(planoCreated));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um plano", description = "Atualiza um plano")
    public ResponseEntity<PlanoResponseDTO> update(@Valid @RequestBody PlanoUpdateDTO planoUpdateDTO,
                                           @PathVariable Integer id) {
        Plano plano = planoMapper.toEntity(planoUpdateDTO);
        Plano planoUpdated = planoService.update(id, plano);
        return ResponseEntity.status(HttpStatus.OK).body(planoMapper.toDtoResponse(planoUpdated));
    }

    @GetMapping
    @Operation(summary = "Busca todos os planos", description = "Busca todos os planos")
    public ResponseEntity<List<PlanoResponseDTO>> findAll() {
        List<Plano> planos = planoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(planoMapper.toDtoList(planos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um plano por ID", description = "Busca um plano por ID")
    public ResponseEntity<PlanoResponseDTO> findById(@PathVariable Integer id) {
        Plano plano = planoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(planoMapper.toDtoResponse(plano));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um plano", description = "Deleta um plano")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        planoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
