package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.persistence.entities.Schedule;
import br.com.backend.PsiRizerio.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) {
        try {
            var scheduleToReturn = scheduleService.save(schedule);
            return ResponseEntity.status(HttpStatus.CREATED).body(scheduleToReturn);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar consulta");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> update(@PathVariable Long id, @RequestBody Schedule schedule) {
        try {
            var scheduleToUpdate = scheduleService.update(id, schedule);
            return ResponseEntity.ok(scheduleToUpdate);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar consulta");
        }
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> findAll() {
        try {
            var schedules = scheduleService.findAll();
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar consultas");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> findById(@PathVariable Long id) {
        try {
            var schedule = scheduleService.findById(id);
            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            throw new RuntimeException("Consulta não encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            scheduleService.cancelSchedule(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cancelar consulta");
        }
    }
}
