package br.com.backend.application.controller;

import java.util.List;
import java.util.UUID;


import br.com.backend.domain.entity.Relatorio;
import br.com.backend.domain.usecase.RelatorioUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    private final RelatorioUseCase useCase;

    public RelatorioController(RelatorioUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<Relatorio> listarTodos() {
        return useCase.listarTodos();
    }

    @PostMapping
    public Relatorio criar(@RequestBody Relatorio relatorio) {
        return useCase.salvar(relatorio);
    }

    @GetMapping("/sessao/{fkSessao}")
    public ResponseEntity<Relatorio> buscarPorSessao(@PathVariable Integer fkSessao) {
        return ResponseEntity.ok(useCase.buscarPorSessao(fkSessao));
    }

    @GetMapping("/paciente/{fkPaciente}")
    public List<Relatorio> buscarPorPaciente(@PathVariable Integer fkPaciente) {
        return useCase.buscarPorPaciente(fkPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Relatorio> atualizar(@PathVariable UUID id, @RequestBody Relatorio dadosAtualizados) {
        Relatorio atualizado = useCase.atualizar(id, dadosAtualizados);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPorId(@PathVariable UUID id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/sessao/{fkSessao}")
    public ResponseEntity<Void> excluirPorSessao(@PathVariable Integer fkSessao) {
        useCase.excluirPorSessao(fkSessao);
        return ResponseEntity.noContent().build();
    }
}
