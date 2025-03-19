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
import java.util.Optional;

@RestController
@RequestMapping(value = "/usuarios")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
            User userToReturn = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userToReturn);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id,
                          @RequestBody User user) {
            User userToUpdate = userService.update(id, user);
            return ResponseEntity.ok(userToUpdate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
