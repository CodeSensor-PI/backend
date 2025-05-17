package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoCreateDTO;
import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoResponseDTO;
import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoUpdateDTO;
import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.service.SessaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;

    private final SessaoMapper sessaoMapper;

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
    public ResponseEntity<List<SessaoResponseDTO>> findByHorario(@RequestParam LocalDate data,
                                                                 @RequestParam LocalTime hora,
                                                                 @RequestParam LocalTime hora2) {
        List<Sessao> sessoes = sessaoService.findByDtHrSessaoBetween(data, hora, hora2);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody SessaoUpdateDTO sessaoUpdateDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoUpdateDTO);
        Sessao sessaoUpdated = sessaoService.update(id, sessao);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessaoUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sessaoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<List<SessaoResponseDTO>> findByPaciente(@PathVariable Integer id) {
        List<Sessao> sessoes = sessaoService.findByPacienteId(id);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @PutMapping
    @RequestMapping("/cancelar/{id}")
    public ResponseEntity<SessaoResponseDTO> cancelarSessao(@PathVariable Integer id, @Valid @RequestBody SessaoUpdateDTO sessaoUpdateDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoUpdateDTO);
        Sessao sessaoUpdated = sessaoService.cancelarSessao(id, sessao);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessaoUpdated));
    }

    @GetMapping("/status")
    public ResponseEntity<List<SessaoResponseDTO>> findByStatus(@RequestParam StatusSessao status) {
        List<Sessao> sessoes = sessaoService.findByStatusPendente(status);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<LocalTime>> getHorariosDisponiveis(
            @RequestParam LocalDate data,
            @RequestParam LocalTime horaInicio,
            @RequestParam LocalTime horaFim) {
        List<LocalTime> horariosDisponiveis = sessaoService.findHorariosDisponiveis(data, horaInicio, horaFim);
        return ResponseEntity.status(HttpStatus.OK).body(horariosDisponiveis);
    }
}
