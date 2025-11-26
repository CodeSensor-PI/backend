package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.pacienteDTO.*;
import br.com.backend.PsiRizerio.mapper.PacienteMapper;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pacientes")
@Tag(name = "Pacientes", description = "CRUD, autenticação e KPIs de pacientes")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    @Operation(summary = "Criar paciente", description = "Cria um novo paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> create(@Valid @RequestBody PacienteCreateDTO pacienteCreateDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteCreateDTO);
        Paciente pacienteCreated = pacienteService.createUser(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteMapper.toDtoResponse(pacienteCreated));
    }

    @Operation(summary = "Atualizar paciente", description = "Atualiza os dados de um paciente existente")
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> update(
            @Parameter(description = "ID do paciente") @PathVariable Integer id,
            @Valid @RequestBody PacienteUpdateDTO pacienteUpdateDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteUpdateDTO);
        Paciente pacienteUpdated = pacienteService.update(id, paciente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoResponse(pacienteUpdated));
    }

    @Operation(summary = "Alterar senha", description = "Atualiza a senha de um paciente com verificação da senha atual")
    @PutMapping("/{id}/alterar-senha")
    public ResponseEntity<Void> updateSenha(
            @Parameter(description = "ID do paciente") @PathVariable Integer id,
            @Valid @RequestBody PacienteSenhaUpdateDTO pacienteSenhaUpdateDTO,
            Paciente paciente) {
        pacienteService.updateSenha(id, pacienteSenhaUpdateDTO.getSenha(), pacienteSenhaUpdateDTO.getNovaSenha());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar paciente por ID", description = "Retorna os dados de um paciente pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> findById(
            @Parameter(description = "ID do paciente") @PathVariable Integer id) {
        Paciente user = pacienteService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoResponse(user));
    }

    @Operation(summary = "Listar todos os pacientes", description = "Retorna todos os pacientes cadastrados com paginação")
    @GetMapping
    public ResponseEntity<Page<PacienteResponseDTO>> findAll(@Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
                                                            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        Page<Paciente> pacientes = pacienteService.findAll(pageable);
        Page<PacienteResponseDTO> pacientesDto = pacientes.map(pacienteMapper::toDtoResponse);
        return ResponseEntity.status(HttpStatus.OK).body(pacientesDto);
    }

    @Operation(summary = "Desativar paciente", description = "Desativa um paciente no sistema")
    @PutMapping("/{id}/desativar")
    public ResponseEntity<PacienteUpdateDTO> desactivateUser(
            @Parameter(description = "ID do paciente") @PathVariable Integer id) {
        Paciente paciente = pacienteService.desativarPaciente(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoUpdate(paciente));
    }

    @Operation(summary = "Ativar paciente", description = "Ativa novamente um paciente desativado")
    @PutMapping("/{id}/ativar")
    public ResponseEntity<PacienteUpdateDTO> activateUser(
            @Parameter(description = "ID do paciente") @PathVariable Integer id) {
        Paciente paciente = pacienteService.ativarPaciente(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoUpdate(paciente));
    }

    @Operation(summary = "Autenticar paciente", description = "Autentica o paciente e retorna um token JWT")
    @PostMapping("/login")
    public ResponseEntity<PacienteTokenDTO> login(
            @RequestBody PacienteLoginDTO pacienteLoginDto,
            HttpServletResponse response) {

        final Paciente paciente = pacienteMapper.toEntity(pacienteLoginDto);
        PacienteTokenDTO pacienteTokenDto = pacienteService.autenticar(paciente);

        Cookie jwtCookie = new Cookie("jwt", pacienteTokenDto.getToken());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60);
        response.addCookie(jwtCookie);

        pacienteTokenDto.setToken(null);
        return ResponseEntity.ok(pacienteTokenDto);
    }


    @Operation(summary = "Primeiro login do paciente", description = "Adiciona dados complementares no primeiro acesso")
    @PutMapping("/primeiroLogin/{id}")
    public ResponseEntity<PacienteResponseDTO> completeInfos(
            @Parameter(description = "ID do paciente") @PathVariable Integer id,
            @RequestBody PacientePrimeiroLoginDTO pacientePrimeiroLoginDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacientePrimeiroLoginDTO);
        Paciente pacienteUpdated = pacienteService.addDadosPrimeiroLogin(id, paciente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoResponse(pacienteUpdated));
    }

    @Operation(summary = "KPI - % de pacientes inativos", description = "Retorna a porcentagem de pacientes inativos no sistema")
    @GetMapping("/kpi/porcent-inativo")
    public ResponseEntity<PacienteKpiQtdInativosDTO> getQtdInativosKpi() {
        PacienteKpiQtdInativosDTO porcentInativos = pacienteService.getQtdInativosKpi();
        return ResponseEntity.status(HttpStatus.OK).body(porcentInativos);
    }

    @Operation(summary = "Buscar pacientes por nome", description = "Retorna todos os pacientes que contém o nome informado (sem paginação)")
    @GetMapping("/busca")
    public ResponseEntity<List<PacienteResponseDTO>> buscarPorNome(@RequestParam String nome) {
        List<Paciente> pacientes = pacienteService.buscarPorNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toDtoList(pacientes));
    }

    @Operation(summary = "Validação para CPF existente", description = "Retorna um boolean para caso o CPF exista na base de dados")
    @GetMapping("/cpf-existente")
    public ResponseEntity<Boolean> validarCpf(@RequestParam String cpf) {
        boolean existe = pacienteService.cpfExiste(cpf);
        return ResponseEntity.ok(existe);
    }
}
