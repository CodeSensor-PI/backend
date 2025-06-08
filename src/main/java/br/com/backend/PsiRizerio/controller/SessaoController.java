package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.sessaoDTO.*;
import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessoes")
@Tag(name = "Sessões", description = "Gerenciamento de sessões de atendimento dos pacientes")
public class SessaoController {

    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    @Operation(summary = "Criar sessão", description = "Cria uma nova sessão de atendimento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<SessaoResponseDTO> createSessao(@Valid @RequestBody SessaoCreateDTO sessaoCreateDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoCreateDTO);
        Sessao sessaoCreated = sessaoService.createSessao(sessao);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessaoCreated));
    }

    @Operation(summary = "Listar todas as sessões", description = "Retorna todas as sessões cadastradas.")
    @GetMapping
    public ResponseEntity<List<SessaoResponseDTO>> findAll() {
        List<Sessao> sessoes = sessaoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @Operation(summary = "Buscar sessão por ID", description = "Retorna uma sessão específica com base no ID.")
    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> findById(@Parameter(description = "ID da sessão") @PathVariable Integer id) {
        Sessao sessao = sessaoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessao));
    }

    @Operation(summary = "Buscar sessões por horário", description = "Busca sessões com data e horário dentro de um intervalo específico.")
    @GetMapping("/horarios")
    public ResponseEntity<List<SessaoResponseDTO>> findByHorario(
            @RequestParam LocalDate data,
            @RequestParam LocalTime hora,
            @RequestParam LocalTime hora2) {
        List<Sessao> sessoes = sessaoService.findByDtHrSessaoBetween(data, hora, hora2);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @Operation(summary = "Atualizar sessão", description = "Atualiza os dados de uma sessão existente.")
    @PutMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> update(
            @Parameter(description = "ID da sessão a ser atualizada") @PathVariable Integer id,
            @Valid @RequestBody SessaoUpdateDTO sessaoUpdateDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoUpdateDTO);
        Sessao sessaoUpdated = sessaoService.update(id, sessao);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessaoUpdated));
    }

    @Operation(summary = "Deletar sessão", description = "Remove uma sessão do sistema com base no ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID da sessão a ser removida") @PathVariable Integer id) {
        sessaoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Listar sessões por paciente", description = "Retorna todas as sessões associadas a um paciente específico.")
    @GetMapping("/pacientes/{id}")
    public ResponseEntity<List<SessaoResponseDTO>> findByPaciente(@Parameter(description = "ID do paciente") @PathVariable Integer id) {
        List<Sessao> sessoes = sessaoService.findByPacienteId(id);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @Operation(summary = "Cancelar sessão", description = "Cancela uma sessão de atendimento.")
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<SessaoResponseDTO> cancelarSessao(
            @Parameter(description = "ID da sessão a ser cancelada") @PathVariable Integer id,
            @Valid @RequestBody SessaoUpdateDTO sessaoUpdateDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoUpdateDTO);
        Sessao sessaoUpdated = sessaoService.cancelarSessao(id, sessao);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoResponse(sessaoUpdated));
    }

    @Operation(summary = "Buscar sessões por status", description = "Filtra sessões com base no status atual (ex: PENDENTE, CANCELADA).")
    @GetMapping("/status")
    public ResponseEntity<List<SessaoResponseDTO>> findByStatus(
            @RequestParam @Parameter(description = "Status da sessão") StatusSessao status) {
        List<Sessao> sessoes = sessaoService.findByStatusPendente(status);
        return ResponseEntity.status(HttpStatus.OK).body(sessaoMapper.toDtoList(sessoes));
    }

    @Operation(summary = "KPI - Sessões por semana", description = "Retorna os dados de sessões da semana atual e da semana anterior.")
    @GetMapping("/kpi/sessoes-semana")
    public ResponseEntity<List<SessaoKpiResponseDTO>> getKpiSessoesSemana() {
        List<SessaoKpiResponseDTO> kpiData = sessaoService.getKpiSessoesSemanaAtualEAnterior();
        return ResponseEntity.ok(kpiData);
    }

    @Operation(summary = "Buscar sessões do dia", description = "Retorna as sessões agendadas para o dia atual.")
    @GetMapping("/dia")
    public ResponseEntity<List<SessaoDiaResponseDTO>> getSessoesDoDia() {
        List<SessaoDiaResponseDTO> sessoesDoDia = sessaoService.getSessoesDoDia();
        if (sessoesDoDia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(sessoesDoDia);
        }
    }

    @Operation(summary = "KPI - Porcentagem de sessões canceladas", description = "Retorna o percentual de sessões canceladas na semana atual.")
    @GetMapping("/kpi/porcent-cancelada")
    public ResponseEntity<SessaoKpiQtdCanceladasSemanaDTO> getQtdCanceladas() {
        SessaoKpiQtdCanceladasSemanaDTO qtdCanceladas = sessaoService.getKpiQtdCanceladas();
        return ResponseEntity.status(HttpStatus.OK).body(qtdCanceladas);
    }

    @Operation(summary = "Dados do gráfico da dashboard", description = "Fornece dados para gráficos da tela de dashboard.")
    @GetMapping("/dados-grafico")
    public ResponseEntity<List<SessaoDashGraficoDadosDTO>> getDadosGrafico() {
        List<SessaoDashGraficoDadosDTO> dadosGrafico = sessaoService.getDadosGrafico();
        return ResponseEntity.ok(dadosGrafico);
    }
}
