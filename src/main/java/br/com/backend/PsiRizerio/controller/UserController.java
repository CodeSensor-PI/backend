package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioResponseDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioUpdateDTO;
import br.com.backend.PsiRizerio.mapper.UsuarioMapper;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import br.com.backend.PsiRizerio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.h2.engine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
@Tag(name = "Crud de usuario - Controller", description = "Crud de usuario")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserService userService;
    private final UsuarioMapper usuarioMapper;

    public UserController(UserService userService, UsuarioMapper usuarioMapper) {
        this.userService = userService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    @Operation(summary = "Cria um usuário", description = "Cria um usuário")
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioCreateDTO);
        Usuario usuarioCreated = userService.createUser(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDtoResponse(usuarioCreated));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Integer id,
                                                     @Valid @RequestBody UsuarioUpdateDTO usuarioupdateDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioupdateDTO);
        Usuario usuarioUpdated = userService.update(id, usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toDtoResponse(usuarioUpdated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID", description = "Busca um usuário por ID")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Integer id) {
        Usuario user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toDtoResponse(user));
    }

    @GetMapping
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        List<Usuario> usuarios = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toDtoList(usuarios));
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<UsuarioUpdateDTO> deactivateUser(@PathVariable Integer id) {
        Usuario usuario = userService.desativarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toDtoUpdate(usuario));
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<UsuarioUpdateDTO> activateUser(@PathVariable Integer id) {
        Usuario usuario = userService.ativarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toDtoUpdate(usuario));
    }
}
