package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaResponseDTO;
import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaCreateDTO;
import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaUpdateDTO;
import br.com.backend.PsiRizerio.mapper.PreferenciaMapper;
import br.com.backend.PsiRizerio.persistence.entities.Preferencia;
import br.com.backend.PsiRizerio.service.PreferenciaService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preferencias")
public class PreferenciaController {

    private final PreferenciaService preferenciaService;
    private final PreferenciaMapper preferenciaMapper;

    public PreferenciaController(PreferenciaService preferenciaService, PreferenciaMapper preferenciaMapper) {
        this.preferenciaService = preferenciaService;
        this.preferenciaMapper = preferenciaMapper;
    }

    @PostMapping
    public ResponseEntity<PreferenciaResponseDTO> createPreferencia(
            @Valid @RequestBody PreferenciaCreateDTO preferenciaCreateDTO) {
        Preferencia preferencia = preferenciaMapper.toEntity(preferenciaCreateDTO);
        return ResponseEntity.status(201)
                .body(preferenciaMapper.toDtoResponse(preferenciaService.createPreferencia(preferencia)));
    }

    @GetMapping
    public ResponseEntity<List<PreferenciaResponseDTO>> findPreferencia() {
        List<Preferencia> preferencias = preferenciaService.findAllPreferencias();
        return ResponseEntity.status(200)
                .body(preferenciaMapper.toDtoList(preferencias));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenciaResponseDTO> findPreferenciaById(@PathVariable Integer id) {
        Preferencia preferencia = preferenciaService.findPreferenciaById(id);
        return ResponseEntity.status(200)
                .body(preferenciaMapper.toDtoResponse(preferencia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreferenciaResponseDTO> updatePreferencia(@PathVariable Integer id,
                                                                  @Valid @RequestBody PreferenciaUpdateDTO preferenciaUpdateDTO) {
        Preferencia preferencia = preferenciaMapper.toEntity(preferenciaUpdateDTO);
        return ResponseEntity.status(200)
                .body(preferenciaMapper.toDtoResponse(preferenciaService.updatePreferencia(id, preferencia)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreferencia(@PathVariable Integer id) {
        preferenciaService.deletePreferencia(id);
        return ResponseEntity.status(204).build();
    }
}
