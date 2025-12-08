package br.com.backend.application.controller;

import java.util.List;
import java.util.UUID;


import br.com.backend.domain.entity.Relatorio;
import br.com.backend.domain.usecase.RelatorioPDF.RelatorioPdfService;
import br.com.backend.domain.usecase.RelatorioUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    private final RelatorioUseCase useCase;
    private final RelatorioPdfService relatorioPdfService;

    // proteger contra page sizes muito grandes
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

    public RelatorioController(RelatorioUseCase useCase, RelatorioPdfService relatorioPdfService) {
        this.useCase = useCase;
        this.relatorioPdfService = relatorioPdfService;
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

    // Endpoint paginado: aceita ?page=&size=&sort=
    // Agora `size` pode ser alterado pelo front-end (se omitido, usa DEFAULT_PAGE_SIZE)
    @GetMapping("/paciente/{fkPaciente}/pagina")
    public ResponseEntity<Page<Relatorio>> buscarPorPacientePaginado(
            @PathVariable Integer fkPaciente,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", required = false) String sort) {

        // proteger page negativo
        if (page == null || page < 0) {
            page = 0;
        }

        int pageSize = (size != null && size > 0) ? size : DEFAULT_PAGE_SIZE;
        // aplicar limite máximo
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }

        Pageable pageable;
        if (sort != null && !sort.isBlank()) {
            // suporto "campo,asc" ou "campo"
            String[] parts = sort.split(",");
            Sort s;
            if (parts.length == 2) {
                s = Sort.by(Sort.Direction.fromString(parts[1].trim()), parts[0].trim());
            } else {
                s = Sort.by(parts[0].trim());
            }
            pageable = PageRequest.of(page, pageSize, s);
        } else {
            pageable = PageRequest.of(page, pageSize);
        }

        Page<Relatorio> pageResult = useCase.buscarPorPaciente(fkPaciente, pageable);
        return ResponseEntity.ok(pageResult);
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

    @GetMapping("/exportar-pdf/{fkPaciente}")
    public ResponseEntity<byte[]> exportarRelatoriosPdf(@PathVariable Integer fkPaciente) {
        byte[] pdf = relatorioPdfService.gerarRelatorioPdf(fkPaciente);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorios_paciente_" + fkPaciente + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
