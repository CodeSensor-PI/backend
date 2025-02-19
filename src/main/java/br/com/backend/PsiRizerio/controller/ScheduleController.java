package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.exception.schedule.FindScheduleException;
import br.com.backend.PsiRizerio.exception.schedule.SaveScheduleException;
import br.com.backend.PsiRizerio.model.ScheduleDTO;
import br.com.backend.PsiRizerio.persistence.entities.Schedule;
import br.com.backend.PsiRizerio.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/schedule")
@Tag(name = "Crud de Agendamento - Controller", description = "Crud de Agendamento")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @Operation(summary = "Cria um agendamento", description = "Cria um agendamento")
    public ResponseEntity<ScheduleDTO> create(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            var schedule = scheduleService.save(scheduleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
        } catch (SaveScheduleException sse) {
            throw new SaveScheduleException("Erro ao salvar consulta", sse);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um agendamento", description = "Atualiza um agendamento")
    public ResponseEntity<ScheduleDTO> update(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO) {
        try {
            var schedule = scheduleService.update(id, scheduleDTO);
            return ResponseEntity.ok(schedule);
        } catch (FindScheduleException fse) {
            throw new FindScheduleException("Consulta não encontrada");
        } catch (SaveScheduleException sse) {
            throw new SaveScheduleException("Erro ao salvar consulta", sse);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar consulta");
        }
    }

    @GetMapping
    @Operation(summary = "Busca todos os agendamentos", description = "Busca todos os agendamentos")
    public ResponseEntity<List<Schedule>> findAll() {
        try {
            var schedules = scheduleService.findAll();
            return ResponseEntity.ok(schedules);
        } catch (FindScheduleException fse) {
            throw new FindScheduleException("Erro ao buscar consultas");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um agendamento por ID", description = "Busca um agendamento por ID")
    public ResponseEntity<ScheduleDTO> findById(@PathVariable Long id) {
        try {
            var schedule = scheduleService.findById(id);
            return ResponseEntity.ok(schedule);
        } catch (FindScheduleException fse) {
            throw new FindScheduleException("Consulta não encontrada");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um agendamento por ID", description = "Deleta um agendamento por ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            scheduleService.cancelSchedule(id);
            return ResponseEntity.noContent().build();
        } catch (FindScheduleException fse) {
            throw new FindScheduleException("Consulta não encontrada");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cancelar consulta");
        }
    }
}
