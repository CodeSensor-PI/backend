package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.SaveUserException;
import br.com.backend.PsiRizerio.mapper.UserMapper;
import br.com.backend.PsiRizerio.model.UserDTO;
import br.com.backend.PsiRizerio.persistence.entities.User;
import br.com.backend.PsiRizerio.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Autowired
    private UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        try {
            var persistedUser = userRepository.save(mapper.toEntity(userDTO));
            return mapper.toDto(persistedUser);
        } catch (Exception e) {
            log.error("Error creating user", e);
            throw new SaveUserException("Error creating user");
        }
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDto(userRepository.save(mapper.toEntity(userDTO)));

    }

    public UserDTO findById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDto(user);
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting user", e);
            throw new RuntimeException("Error deleting user");
        }
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return mapper.toDto(users);
    }

}
