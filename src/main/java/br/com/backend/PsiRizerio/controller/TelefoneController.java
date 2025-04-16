package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneCreateDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneRespondeDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneUpdateDTO;
import br.com.backend.PsiRizerio.mapper.TelefoneMapper;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import br.com.backend.PsiRizerio.service.TelefoneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefones")
public class TelefoneController {

    private final TelefoneService telefoneService;

    private final TelefoneMapper telefoneMappper;

    public TelefoneController(TelefoneService telefoneService, TelefoneMapper telefoneMappper) {
        this.telefoneService = telefoneService;
        this.telefoneMappper = telefoneMappper;
    }

    @PostMapping
    public ResponseEntity<TelefoneRespondeDTO> createTelefone(@Valid @RequestBody TelefoneCreateDTO telefoneCreateDTO) {
        Telefone telefone = telefoneMappper.toEntity(telefoneCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(telefoneMappper.toDtoResponse(telefoneService.createTelefone(telefone)));
    }

    @GetMapping
    public ResponseEntity<List<TelefoneRespondeDTO>> findAll() {
        List<Telefone> telefones = telefoneService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(telefoneMappper.toDtoList(telefones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefoneRespondeDTO> findById(@PathVariable Integer id) {
        Telefone telefone = telefoneService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(telefoneMappper.toDtoResponse(telefone));
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<List<TelefoneRespondeDTO>> findByUsuarioId(@PathVariable Integer id) {
        List<Telefone> telefones = telefoneService.findByFkCliente(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(telefoneMappper.toDtoList(telefones));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelefoneRespondeDTO> update(@PathVariable Integer id,
                                                      @Valid @RequestBody TelefoneUpdateDTO telefoneUpdateDTO) {
        Telefone telefone = telefoneMappper.toEntity(telefoneUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(telefoneMappper.toDtoResponse(telefoneService.update(id, telefone)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        telefoneService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
