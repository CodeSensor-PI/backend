package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.exception.user.DeleteUserException;
import br.com.backend.PsiRizerio.exception.user.FindUserException;
import br.com.backend.PsiRizerio.exception.user.SaveUserException;
import br.com.backend.PsiRizerio.model.UserDTO;
import br.com.backend.PsiRizerio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        try {
            var userToReturn = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userToReturn);
        } catch (SaveUserException sue) {
            log.error("Erro ao salvar consulta: {}", sue.getMessage(), sue);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário")
    public UserDTO update(@PathVariable Long id,
                          @RequestBody UserDTO userDTO) {
       try {
            return userService.update(id, userDTO);
        } catch (FindUserException fue) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error updating user");
       } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID", description = "Busca um usuário por ID")
    public UserDTO findById(@PathVariable Long id) {
        try {
            return userService.findById(id);
        } catch (FindUserException fue) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error finding user");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error finding user");
        }
    }

    @GetMapping
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários")
    public ResponseEntity<List<UserDTO>> findAll() {
        if (userService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        try {
            var users = userService.findAll();
            return ResponseEntity.ok(users);
        } catch (FindUserException fue) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error finding users");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error finding users");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um usuário", description = "Deleta um usuário")
    public void delete(@PathVariable Long id) {
        try {
            userService.delete(id);
        } catch (DeleteUserException due) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error deleting user");
        } catch (FindUserException fue) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error finding user");
        }
    }
}
