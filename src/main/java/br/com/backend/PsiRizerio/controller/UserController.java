package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.exception.DeleteUserException;
import br.com.backend.PsiRizerio.exception.FindUserException;
import br.com.backend.PsiRizerio.exception.SaveUserException;
import br.com.backend.PsiRizerio.model.UserDTO;
import br.com.backend.PsiRizerio.persistence.entities.User;
import br.com.backend.PsiRizerio.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        try {
            var userToReturn = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userToReturn);
        } catch (SaveUserException sue) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user");
        }
    }

    @PutMapping("/{id}")
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
