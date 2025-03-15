package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.persistence.entities.User;
import br.com.backend.PsiRizerio.persistence.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (user == null || user.getEmail() == null || user.getCpf() == null || user.getNome() == null
                || user.getSenha() == null || user.getFk_endereco() == null || user.getFk_plano() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário, Campos inválidos");
        }

        if (userRepository.existsByEmailOrCpfIgnoreCase(user.getEmail(), user.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ou CPF já cadastrados");
        }

        try {
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(user.getCreatedAt());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error saving user", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar usuário", e);
        }
    }


    public User update(Integer id, User user) {
        if(user == null || user.getEmail() == null || user.getCpf() == null || user.getNome() == null
                || user.getSenha() == null || user.getFk_endereco() == null || user.getFk_plano() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário, Campos inválidos");
        }

        if (userRepository.existsByEmailOrCpfAndIdNot(user.getEmail(), user.getCpf(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ou CPF já cadastrados");
        }

        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        try {
            user.setId(null);
            return userRepository.save(userToUpdate);
        } catch (Exception e) {
            log.error("Error updating user", e);
            throw new RuntimeException("Error updating user");
        }
    }

    public User findById(Integer id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        try {
            return user;
        } catch (Exception e) {
            log.error("Error finding user", e);
            throw new RuntimeException("Error finding user");
        }
    }

    public void delete(Integer id) {

        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting user", e);
            throw new RuntimeException("Error deleting user");
        }
    }

    public List<User> findAll() {
        if (userRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No content");
        }

        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error finding users", e);
            throw new RuntimeException("Error finding users");
        }
    }

}
