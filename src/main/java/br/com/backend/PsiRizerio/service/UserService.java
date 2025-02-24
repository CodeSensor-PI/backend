package br.com.backend.PsiRizerio.service;

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

    @Autowired
    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error creating user", e);
            throw new RuntimeException("Error creating user");
        }
    }

    public User update(Long id, User user) {
        try {
            User userToUpdate = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            userToUpdate.setName(user.getName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setPhone(user.getPhone());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setCpf(user.getCpf());
            return userRepository.save(userToUpdate);
        } catch (Exception e) {
            log.error("Error updating user", e);
            throw new RuntimeException("Error updating user");
        }
    }

    public User findById(Long id) {
        try {
            return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            log.error("Error finding user", e);
            throw new RuntimeException("Error finding user");
        }
    }

    public void delete(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new RuntimeException("User not found");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting user", e);
            throw new RuntimeException("Error deleting user");
        }
    }

    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error finding users", e);
            throw new RuntimeException("Error finding users");
        }
    }

}
