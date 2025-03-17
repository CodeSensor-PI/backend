package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.PlanoDTO;
import br.com.backend.PsiRizerio.exception.user.FindUserException;
import br.com.backend.PsiRizerio.exception.user.SaveUserException;
import br.com.backend.PsiRizerio.exception.user.UpdateUserException;
import br.com.backend.PsiRizerio.mapper.UserMapper;
import br.com.backend.PsiRizerio.dto.UserDTO;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
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
    private final UserMapper userMapper;

    @Autowired
    private UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.userMapper = mapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO == null || userDTO.getEmail() == null || userDTO.getCpf() == null || userDTO.getNome() == null
                || userDTO.getSenha() == null || userDTO.getFkEndereco() == null || userDTO.getFkPlano() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário, Campos inválidos");
        }

        if (userRepository.existsByEmailOrCpfIgnoreCase(userDTO.getEmail(), userDTO.getCpf())) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ou CPF já cadastrados ou invalidos");

        if (!isValidCPF(userDTO.getCpf())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF inválido");

        if (!isValidEmail(userDTO.getEmail())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email inválido");

        try {
            userDTO.setCreatedAt(LocalDateTime.now());
            userDTO.setUpdatedAt(LocalDateTime.now());
            User userToMapper = userMapper.toEntity(userDTO);
            User userToSave = userRepository.save(userToMapper);
            return userMapper.toDto(userToSave);
        } catch (SaveUserException sue) {
            log.error("Error creating user", sue);
            throw new SaveUserException("Error creating user");
        } catch (Exception e) {
            log.error("Error creating user", e);
            throw new RuntimeException("Error creating user");
        }
    }

    public UserDTO update(Integer id, UserDTO userDTO) {
        User usersToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(userDTO.getEmail(), id)
        || userRepository.existsByCpfIgnoreCaseAndIdNot(userDTO.getCpf(), id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ou CPF já cadastrados");
        }

        if (!isValidCPF(userDTO.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF inválido");
        }

        if (!isValidEmail(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email inválido");
        }

        try {
            usersToUpdate.setNome(userDTO.getNome());
            usersToUpdate.setCpf(userDTO.getCpf());
            usersToUpdate.setEmail(userDTO.getEmail());
            usersToUpdate.setSenha(userDTO.getSenha());
            usersToUpdate.setFkEndereco(new Endereco(userDTO.getFkEndereco().getId()));
            usersToUpdate.setFkPlano(new Plano(userDTO.getFkPlano().getId()));
            usersToUpdate.setUpdatedAt(LocalDateTime.now());
            User userToSave = userRepository.save(usersToUpdate);
            return userMapper.toDto(userToSave);
        } catch (Exception e) {
            log.error("Error updating user", e);
            throw new RuntimeException("Error updating user");
        }
    }


    public UserDTO findById(Integer id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
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
        return userMapper.toDto(users);
    }

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        int[] pesoCPF = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        int digito2 = calcularDigito(cpf.substring(0, 9) + digito1, new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2});

        return cpf.equals(cpf.substring(0, 9) + digito1 + digito2);
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += (str.charAt(i) - '0') * peso[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
