package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteLoginDTO;
import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteTokenDTO;
import br.com.backend.PsiRizerio.dto.psicologoDTO.*;
import br.com.backend.PsiRizerio.mapper.PsicologoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import br.com.backend.PsiRizerio.service.PsicologoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/psicologos")
public class PsicologoController {

    private final PsicologoService psicologoService;
    private final PsicologoMapper psicologoMapper;

    @PostMapping
    public PsicologoResponseDTO createPsicologo(@Valid @RequestBody PsicologoCreateDTO psicologoCreateDTO) {
        Psicologo psicologo = psicologoMapper.toEntity(psicologoCreateDTO);
        psicologoService.createPsicologo(psicologo);
        return psicologoMapper.toDtoResponse(psicologo);
    }

    @GetMapping("/{id}")
    public PsicologoResponseDTO findPsicologoById(@PathVariable Integer id) {
        Psicologo psicologo = psicologoService.findById(id);
        return psicologoMapper.toDtoResponse(psicologo);
    }

    @GetMapping
    public List<PsicologoResponseDTO> findAllPsicologos() {
        return psicologoMapper.toDtoList(psicologoService.findAll());
    }

    @PutMapping("/{id}")
    public PsicologoResponseDTO updatePsicologo(@PathVariable Integer id,
                                                @Valid @RequestBody PsicologoUpdateDTO psicologoUpdateDTO) {
        Psicologo psicologo = psicologoMapper.toEntity(psicologoUpdateDTO);
        return psicologoMapper.toDtoResponse(psicologoService.update(id, psicologo));
    }

    @PutMapping("/{id}/ativar")
    public void ativarPsicologo(@PathVariable Integer id) {
        psicologoService.ativarPsicologo(id);
    }

    @PutMapping("/{id}/desativar")
    public void desativarPsicologo(@PathVariable Integer id) {
        psicologoService.desativarPsicologo(id);
    }

    @PostMapping("/login")
    public ResponseEntity<PsicologoTokenDTO> login(@RequestBody PsicologoLoginDTO psicologoLoginDTO) {

        final Psicologo psicologo = psicologoMapper.toEntity(psicologoLoginDTO);
        PsicologoTokenDTO psicologoTokenDTO = this.psicologoService.autenticar(psicologo);

        return ResponseEntity.status(200).body(psicologoTokenDTO);
    }

    @PutMapping("/{id}/alterar-senha")
    public ResponseEntity<Void> updateSenha(@PathVariable Integer id,
                                            @Valid @RequestBody PsicologoSenhaUpdateDTO psicologoSenhaUpdateDTO) {
        psicologoService.updateSenha(id, psicologoSenhaUpdateDTO.getSenha(), psicologoSenhaUpdateDTO.getNovaSenha());
        return ResponseEntity.ok().build();
    }
}
