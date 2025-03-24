package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.userDTO.UsuarioCreateDTO;
import br.com.backend.PsiRizerio.dto.userDTO.UsuarioUpdateDTO;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.mapper.UserMapper;
import br.com.backend.PsiRizerio.dto.userDTO.UsuarioResponseDTO;
import br.com.backend.PsiRizerio.persistence.entities.User;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Autowired
    private UserService(UserRepository userRepository, UserMapper mapper, EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.userRepository = userRepository;
        this.userMapper = mapper;
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    public UsuarioCreateDTO createUser(UsuarioCreateDTO usuarioCreateDTO) {
        if (userRepository.existsByEmailOrCpfIgnoreCase(usuarioCreateDTO.getEmail(), usuarioCreateDTO.getCpf())) throw new EntidadeConflitoException();

        if (!isValidEmail(usuarioCreateDTO.getEmail())) throw new EntidadeInvalidaException();

        usuarioCreateDTO.setCreatedAt(LocalDateTime.now());
        User userToMapper = userMapper.toEntity(usuarioCreateDTO);
        User userToSave = userRepository.save(userToMapper);
        return userMapper.toDto(userToSave);
    }

    public UsuarioUpdateDTO update(Integer id, UsuarioUpdateDTO usuarioupdateDTO) {
        User usersToUpdate = userRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        if (userRepository.existsByCpfIgnoreCaseAndIdNot(usuarioupdateDTO.getCpf(), id) ||
                userRepository.existsByEmailIgnoreCaseAndIdNot(usuarioupdateDTO.getEmail(), id)) {
            throw new EntidadeConflitoException();
        }

        if (!isValidEmail(usuarioupdateDTO.getEmail())) throw new EntidadeInvalidaException();

        User usuarioAtualizado = userMapper.toEntity(usuarioupdateDTO);

        usersToUpdate.setNome(usuarioAtualizado.getNome());
        usersToUpdate.setCpf(usuarioAtualizado.getCpf());
        usersToUpdate.setEmail(usuarioAtualizado.getEmail());
        usersToUpdate.setFkEndereco(usuarioAtualizado.getFkEndereco());
        usersToUpdate.setFkPlano(usuarioAtualizado.getFkPlano());
        usersToUpdate.setUpdatedAt(LocalDateTime.now());

        User userToSave = userRepository.save(usersToUpdate);
        return userMapper.toDtoUpdate(userToSave);
    }


    public UsuarioResponseDTO findById(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        User user = userRepository.findById(id).orElseThrow((EntidadeNaoEncontradaException::new));

        return userMapper.toDtoResponse(user);
    }

    public void delete(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        if (!userRepository.existsById(id)) throw new EntidadeNaoEncontradaException();

        userRepository.deleteById(id);
    }

    public List<UsuarioResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new EntidadeNaoEncontradaException();
        return userMapper.toDtoList(users);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
