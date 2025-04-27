package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoRespondeDTO;
import br.com.backend.PsiRizerio.service.AvaliacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoRespondeDTO> createAvaliacao(@RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) {
        AvaliacaoRespondeDTO createdAvaliacao = avaliacaoService.createAvaliacao(avaliacaoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAvaliacao);
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoRespondeDTO>> getAllAvaliacoes() {
        List<AvaliacaoRespondeDTO> avaliacoes = avaliacaoService.getAllAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Integer id) {
        avaliacaoService.deleteAvaliacaoById(id);
        return ResponseEntity.noContent().build();
    }
}