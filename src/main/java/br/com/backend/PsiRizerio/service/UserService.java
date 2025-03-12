package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.user.FindUserException;
import br.com.backend.PsiRizerio.exception.user.SaveUserException;
import br.com.backend.PsiRizerio.exception.user.UpdateUserException;
import br.com.backend.PsiRizerio.mapper.UserMapper;
import br.com.backend.PsiRizerio.dto.UserDTO;
import br.com.backend.PsiRizerio.persistence.entities.User;
import br.com.backend.PsiRizerio.persistence.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            User user = mapper.toEntity(userDTO);
            userRepository.save(user);
            log.info("User created: {}", user);
            return mapper.toDto(user);
        } catch (SaveUserException sue) {
            log.error("Error creating user", sue);
            throw new SaveUserException("Error creating user");
        } catch (Exception e) {
            log.error("Error creating user", e);
            throw new RuntimeException("Error creating user");
        }
    }

    public UserDTO update(Integer id, UserDTO userDTO) {
            User usersToUpdate = userRepository.findById(id).orElseThrow(() -> new UpdateUserException("User not found"));;
            userRepository.save(usersToUpdate);
            return mapper.toDto(usersToUpdate);
    }

    public UserDTO findById(Integer id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDto(user);
    }

    public void delete(Integer id) {
        var user = userRepository.findById(id).orElseThrow(() -> new FindUserException("User not found"));
        userRepository.delete(user);
    }

    public List<UserDTO> findAll() {
        if (userRepository.findAll().isEmpty()) {
            log.error("No users found");
            throw new RuntimeException("No users found");
        }

        List<User> users = userRepository.findAll();
        return mapper.toDto(users);
    }

}
