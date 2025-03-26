package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioResponseDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioUpdateDTO;
import br.com.backend.PsiRizerio.service.UserService;
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
@RequestMapping(value = "/usuarios")
@Tag(name = "Crud de usuario - Controller", description = "Crud de usuario")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @Operation(summary = "Cria um usuário", description = "Cria um usuário")
    public ResponseEntity<UsuarioCreateDTO> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioCreateDTO user = userService.createUser(usuarioCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário")
    public ResponseEntity<UsuarioUpdateDTO> update(@PathVariable Integer id,
                                                     @Valid @RequestBody UsuarioUpdateDTO usuarioupdateDTO) {
        UsuarioUpdateDTO user = userService.update(id, usuarioupdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID", description = "Busca um usuário por ID")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Integer id) {
        UsuarioResponseDTO user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        List<UsuarioResponseDTO> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<UsuarioUpdateDTO> deactivateUser(@PathVariable Integer id) {
        UsuarioUpdateDTO user = userService.desativarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<UsuarioUpdateDTO> activateUser(@PathVariable Integer id) {
        UsuarioUpdateDTO user = userService.ativarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
