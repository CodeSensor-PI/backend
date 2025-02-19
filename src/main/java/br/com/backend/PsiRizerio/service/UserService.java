package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.user.SaveUserException;
import br.com.backend.PsiRizerio.mapper.UserMapper;
import br.com.backend.PsiRizerio.model.UserDTO;
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
            var persistedUser = userRepository.save(mapper.toEntity(userDTO));
            return mapper.toDto(persistedUser);
        } catch (Exception e) {
            log.error("Error creating user", e);
            throw new SaveUserException("Error creating user");
        }
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        try {
            if (userRepository.findById(id).isEmpty()) {
                throw new RuntimeException("User not found");
            }

            User usersToUpdate = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            usersToUpdate.setName(userDTO.getName());
            usersToUpdate.setEmail(userDTO.getEmail());
            usersToUpdate.setPassword(userDTO.getPassword());
            usersToUpdate.setPhone(userDTO.getPhone());
            usersToUpdate.setAddress(userDTO.getAddress());
            usersToUpdate.setCpf(userDTO.getCpf());
            userRepository.save(usersToUpdate);
            return mapper.toDto(usersToUpdate);
        } catch (Exception e) {
            log.error("Error updating user", e);
            throw new RuntimeException("Error updating user");
        }

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
