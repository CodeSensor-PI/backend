package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoCreateDTO;
import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoResponseDTO;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.service.SessaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;

    private final SessaoMapper sessaoMapper;

    public SessaoController(SessaoService sessaoService, SessaoMapper sessaoMapper) {
        this.sessaoService = sessaoService;
        this.sessaoMapper = sessaoMapper;
    }

    @PostMapping
    public ResponseEntity<SessaoResponseDTO> createSessao(@Valid @RequestBody SessaoCreateDTO sessaoCreateDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoCreateDTO);
        Sessao sessaoCreated = sessaoService.createSessao(sessao);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessaoCreated));
    }

    @GetMapping
    public ResponseEntity<List<SessaoResponseDTO>> findAll() {
        List<Sessao> sessoes = sessaoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> findById(@PathVariable Integer id) {
        Sessao sessao = sessaoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessao));
    }

    @GetMapping("/horarios")
    public ResponseEntity<List<SessaoResponseDTO>> findByHorario(@RequestParam LocalDateTime start,
                                                                 @RequestParam LocalDateTime end) {
        List<Sessao> sessoes = sessaoService.findByDtHrSessaoBetween(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody SessaoCreateDTO sessaoCreateDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoCreateDTO);
        Sessao sessaoUpdated = sessaoService.update(id, sessao);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessaoUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sessaoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<List<SessaoResponseDTO>> findByUsuario(@PathVariable Integer id) {
        List<Sessao> sessoes = sessaoService.findByUsuarioId(id);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }
}
