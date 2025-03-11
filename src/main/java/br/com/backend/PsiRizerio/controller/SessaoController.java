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
        var schedule = sessaoService.save(sessaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um agendamento", description = "Atualiza um agendamento")
    public ResponseEntity<SessaoDTO> update(@PathVariable Long id, @RequestBody SessaoDTO sessaoDTO) {
        var schedule = sessaoService.update(id, sessaoDTO);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping
    @Operation(summary = "Busca todos os agendamentos", description = "Busca todos os agendamentos")
    public ResponseEntity<List<Sessao>> findAll() {
        var schedules = sessaoService.findAll();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um agendamento por ID", description = "Busca um agendamento por ID")
    public ResponseEntity<SessaoDTO> findById(@PathVariable Long id) {
        var schedule = sessaoService.findById(id);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um agendamento por ID", description = "Deleta um agendamento por ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sessaoService.cancelSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
