package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.SessaoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/schedule")
@Tag(name = "Crud de Agendamento - Controller", description = "Crud de Agendamento")
public class SessaoController {

    private final SessaoService sessaoService;
    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    @Operation(summary = "Cria um agendamento", description = "Cria um agendamento")
    public ResponseEntity<SessaoDTO> create(@RequestBody SessaoDTO sessaoDTO) {
        SessaoDTO sessao = sessaoService.save(sessaoDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(sessao);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um agendamento", description = "Atualiza um agendamento")
    public ResponseEntity<SessaoDTO> update(@PathVariable Integer id, @RequestBody SessaoDTO sessaoDTO) {
        SessaoDTO sessao = sessaoService.update(id, sessaoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(sessao);
    }

    @GetMapping
    @Operation(summary = "Busca todos os agendamentos", description = "Busca todos os agendamentos")
    public ResponseEntity<List<SessaoDTO>> findAll() {
        List<SessaoDTO> sessao = sessaoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sessao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um agendamento por ID", description = "Busca um agendamento por ID")
    public ResponseEntity<SessaoDTO> findById(@PathVariable Integer id) {
        SessaoDTO sessao = sessaoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(sessao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um agendamento por ID", description = "Deleta um agendamento por ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sessaoService.cancelSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
