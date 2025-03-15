package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.service.SessaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    public ResponseEntity<Sessao> create(@RequestBody Sessao sessao) {
            Sessao scheduleToReturn = sessaoService.save(sessao);
            return ResponseEntity.status(HttpStatus.CREATED).body(scheduleToReturn);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sessao> update(@PathVariable Integer id, @RequestBody Sessao sessao) {
            Sessao scheduleToUpdate = sessaoService.update(id, sessao);
            return ResponseEntity.ok(scheduleToUpdate);
    }

    @GetMapping
    public ResponseEntity<List<Sessao>> findAll() {
            List<Sessao> schedules = sessaoService.findAll();
            return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> findById(@PathVariable Integer id) {
            Sessao schedule = sessaoService.findById(id);
            return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
            sessaoService.cancelSchedule(id);
            return ResponseEntity.noContent().build();
    }
}
