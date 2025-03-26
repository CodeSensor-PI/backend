package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioUpdateDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.mapper.UsuarioMapper;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioResponseDTO;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Autowired
    private UserService(UsuarioRepository usuarioRepository, UsuarioMapper mapper, EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = mapper;
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    public UsuarioCreateDTO createUser(UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioRepository.existsByEmailOrCpfIgnoreCase(usuarioCreateDTO.getEmail(), usuarioCreateDTO.getCpf())) throw new EntidadeConflitoException();

        if (!isValidEmail(usuarioCreateDTO.getEmail())) throw new EntidadeInvalidaException();

        usuarioCreateDTO.setCreatedAt(LocalDateTime.now());
        Usuario usuarioToMapper = usuarioMapper.toEntity(usuarioCreateDTO);
        Usuario usuarioToSave = usuarioRepository.save(usuarioToMapper);
        return usuarioMapper.toDto(usuarioToSave);
    }

    public UsuarioUpdateDTO update(Integer id, UsuarioUpdateDTO usuarioupdateDTO) {
        Usuario usersToUpdate = usuarioRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        if (usuarioRepository.existsByCpfIgnoreCaseAndIdNot(usuarioupdateDTO.getCpf(), id) ||
                usuarioRepository.existsByEmailIgnoreCaseAndIdNot(usuarioupdateDTO.getEmail(), id)) {
            throw new EntidadeConflitoException();
        }

        if (!isValidEmail(usuarioupdateDTO.getEmail())) throw new EntidadeInvalidaException();

        Usuario usuarioAtualizado = usuarioMapper.toEntity(usuarioupdateDTO);

        usersToUpdate.setNome(usuarioAtualizado.getNome());
        usersToUpdate.setCpf(usuarioAtualizado.getCpf());
        usersToUpdate.setEmail(usuarioAtualizado.getEmail());
        usersToUpdate.setFkEndereco(usuarioAtualizado.getFkEndereco());
        usersToUpdate.setFkPlano(usuarioAtualizado.getFkPlano());
        usersToUpdate.setUpdatedAt(LocalDateTime.now());

        Usuario usuarioToSave = usuarioRepository.save(usersToUpdate);
        return usuarioMapper.toDtoUpdate(usuarioToSave);
    }

    public UsuarioResponseDTO findById(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        Usuario usuario = usuarioRepository.findById(id).orElseThrow((EntidadeNaoEncontradaException::new));

        return usuarioMapper.toDtoResponse(usuario);
    }

    public UsuarioUpdateDTO desativarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        usuario.setStatus(StatusUsuario.INATIVO);
        usuario.setUpdatedAt(LocalDateTime.now());
        Usuario usuarioUpdated = usuarioRepository.save(usuario);
        return usuarioMapper.toDtoUpdate(usuarioUpdated);
    }

    public UsuarioUpdateDTO ativarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        usuario.setStatus(StatusUsuario.ATIVO);
        usuario.setUpdatedAt(LocalDateTime.now());
        Usuario usuarioUpdated = usuarioRepository.save(usuario);

        return usuarioMapper.toDtoUpdate(usuarioUpdated);
    }

    public List<UsuarioResponseDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) throw new EntidadeNaoEncontradaException();
        return usuarioMapper.toDtoList(usuarios);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
