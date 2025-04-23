package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.pacienteDTO.*;
import br.com.backend.PsiRizerio.mapper.PacienteMapper;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pacientes")
@Tag(name = "Crud de usuario - Controller", description = "Crud de usuario")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    public PacienteController(PacienteService pacienteService, PacienteMapper pacienteMapper) {
        this.pacienteService = pacienteService;
        this.pacienteMapper = pacienteMapper;
    }

    @PostMapping
    @Operation(summary = "Cria um usuário", description = "Cria um usuário")
    public ResponseEntity<PacienteResponseDTO> create(@Valid @RequestBody PacienteCreateDTO pacienteCreateDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteCreateDTO);
        Paciente pacienteCreated = pacienteService.createUser(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteMapper.toDtoResponse(pacienteCreated));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário")
    public ResponseEntity<PacienteResponseDTO> update(@PathVariable Integer id,
                                                      @Valid @RequestBody PacienteUpdateDTO usuarioupdateDTO) {
        Paciente paciente = pacienteMapper.toEntity(usuarioupdateDTO);
        Paciente pacienteUpdated = pacienteService.update(id, paciente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoResponse(pacienteUpdated));
    }

    @PutMapping("/{id}/alterar-senha")
    public ResponseEntity<Void> updateSenha(@PathVariable Integer id,
                                               @Valid @RequestBody PacienteSenhaUpdateDTO pacienteSenhaUpdateDTO,
                                               Paciente paciente) {
        pacienteService.updateSenha(id, pacienteSenhaUpdateDTO.getSenha(), pacienteSenhaUpdateDTO.getNovaSenha());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID", description = "Busca um usuário por ID")
    public ResponseEntity<PacienteResponseDTO> findById(@PathVariable Integer id) {
        Paciente user = pacienteService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoResponse(user));
    }

    @GetMapping
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários")
    public ResponseEntity<List<PacienteResponseDTO>> findAll() {
        List<Paciente> pacientes = pacienteService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoList(pacientes));
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<PacienteUpdateDTO> deactivateUser(@PathVariable Integer id) {
        Paciente paciente = pacienteService.desativarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoUpdate(paciente));
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<PacienteUpdateDTO> activateUser(@PathVariable Integer id) {
        Paciente paciente = pacienteService.ativarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoUpdate(paciente));
    }

    @PostMapping("/login")
    public ResponseEntity<PacienteTokenDTO> login(@RequestBody PacienteLoginDTO pacienteLoginDto) {

        final Paciente paciente = pacienteMapper.toEntity(pacienteLoginDto);
        PacienteTokenDTO pacienteTokenDto = this.pacienteService.autenticar(paciente);

        return ResponseEntity.status(200).body(pacienteTokenDto);
    }
}
