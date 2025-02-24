package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.persistence.entities.User;
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
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            var userToReturn = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userToReturn);
        } catch (Exception e) {
            log.error("Erro ao salvar consulta: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                          @RequestBody User user) {
       try {
           var userToReturn = userService.update(id, user);
           return ResponseEntity.ok(userToReturn);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error updating user");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        try {
            var user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error finding user");
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        try {
            if (userService.findAll().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            var users = userService.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error finding users");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error deleting user");
        }
    }
}
