package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteLoginDTO;
import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteTokenDTO;
import br.com.backend.PsiRizerio.dto.psicologoDTO.*;
import br.com.backend.PsiRizerio.mapper.PsicologoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import br.com.backend.PsiRizerio.service.PsicologoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/psicologos")
@Tag(name = "Psicólogos", description = "Gerenciamento e autenticação de psicólogos do sistema")
public class PsicologoController {

    private final PsicologoService psicologoService;
    private final PsicologoMapper psicologoMapper;

    @Operation(summary = "Criar psicólogo", description = "Cria um novo psicólogo no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Psicólogo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public PsicologoResponseDTO createPsicologo(@Valid @RequestBody PsicologoCreateDTO psicologoCreateDTO) {
        Psicologo psicologo = psicologoMapper.toEntity(psicologoCreateDTO);
        psicologoService.createPsicologo(psicologo);
        return psicologoMapper.toDtoResponse(psicologo);
    }

    @Operation(summary = "Buscar psicólogo por ID", description = "Retorna os dados de um psicólogo específico")
    @GetMapping("/{id}")
    public PsicologoResponseDTO findPsicologoById(
            @Parameter(description = "ID do psicólogo") @PathVariable Integer id) {
        Psicologo psicologo = psicologoService.findById(id);
        return psicologoMapper.toDtoResponse(psicologo);
    }

    @Operation(summary = "Listar psicólogos", description = "Retorna todos os psicólogos cadastrados no sistema")
    @GetMapping
    public List<PsicologoResponseDTO> findAllPsicologos() {
        return psicologoMapper.toDtoList(psicologoService.findAll());
    }

    @Operation(summary = "Atualizar psicólogo", description = "Atualiza os dados de um psicólogo existente")
    @PutMapping("/{id}")
    public PsicologoResponseDTO updatePsicologo(
            @Parameter(description = "ID do psicólogo a ser atualizado") @PathVariable Integer id,
            @Valid @RequestBody PsicologoUpdateDTO psicologoUpdateDTO) {
        Psicologo psicologo = psicologoMapper.toEntity(psicologoUpdateDTO);
        return psicologoMapper.toDtoResponse(psicologoService.update(id, psicologo));
    }

    @Operation(summary = "Ativar psicólogo", description = "Ativa o psicólogo para uso no sistema")
    @PutMapping("/{id}/ativar")
    public void ativarPsicologo(
            @Parameter(description = "ID do psicólogo a ser ativado") @PathVariable Integer id) {
        psicologoService.ativarPsicologo(id);
    }

    @Operation(summary = "Desativar psicólogo", description = "Desativa o psicólogo no sistema")
    @PutMapping("/{id}/desativar")
    public void desativarPsicologo(
            @Parameter(description = "ID do psicólogo a ser desativado") @PathVariable Integer id) {
        psicologoService.desativarPsicologo(id);
    }

    @Operation(summary = "Autenticar psicólogo", description = "Autentica o psicólogo e retorna um token JWT")
    @PostMapping("/login")
    public ResponseEntity<PsicologoTokenDTO> login(
            @RequestBody PsicologoLoginDTO psicologoLoginDTO,
            HttpServletResponse response) {

        final Psicologo psicologo = psicologoMapper.toEntity(psicologoLoginDTO);
        PsicologoTokenDTO psicologoTokenDTO = this.psicologoService.autenticar(psicologo);

        Cookie jwtCookie = new Cookie("jwt", psicologoTokenDTO.getToken());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // use HTTPS em produção
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60); // 1h
        response.addCookie(jwtCookie);

        // Retorna os dados do psicólogo sem o token no body
        psicologoTokenDTO.setToken(null);
        return ResponseEntity.ok(psicologoTokenDTO);
    }

    @Operation(summary = "Alterar senha", description = "Altera a senha do psicólogo com validação da senha atual")
    @PutMapping("/{id}/alterar-senha")
    public ResponseEntity<Void> updateSenha(
            @Parameter(description = "ID do psicólogo") @PathVariable Integer id,
            @Valid @RequestBody PsicologoSenhaUpdateDTO psicologoSenhaUpdateDTO) {
        psicologoService.updateSenha(id, psicologoSenhaUpdateDTO.getSenha(), psicologoSenhaUpdateDTO.getNovaSenha());
        return ResponseEntity.ok().build();
    }
}
