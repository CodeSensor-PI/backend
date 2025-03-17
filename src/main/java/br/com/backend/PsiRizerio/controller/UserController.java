package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.dto.UserDTO;
import br.com.backend.PsiRizerio.persistence.entities.User;
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
@RequestMapping(value = "/v1/user")
@Tag(name = "Crud de usuario - Controller", description = "Crud de usuario")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @Operation(summary = "Cria um usuário", description = "Cria um usuário")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        UserDTO user = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id,
                          @Valid @RequestBody UserDTO userDTO) {
        UserDTO user = userService.update(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID", description = "Busca um usuário por ID")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um usuário", description = "Deleta um usuário")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
