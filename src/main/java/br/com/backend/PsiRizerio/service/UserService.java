package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioTokenDTO;
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

import br.com.backend.PsiRizerio.security.AutenticacaoProvider;
import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final UsuarioMapper usuarioMapper;

    public UserService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, GerenciadorTokenJwt gerenciadorTokenJwt, AuthenticationManager authenticationManager, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
        this.usuarioMapper = usuarioMapper;
    }

    public Usuario createUser(Usuario usuario) {
        if (usuarioRepository.existsByEmailOrCpfIgnoreCase(usuario.getEmail(), usuario.getCpf())
        && usuario.getStatus() == StatusUsuario.ATIVO) throw new EntidadeConflitoException();

        if (!isValidEmail(usuario.getEmail())) throw new EntidadeInvalidaException();

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        usuario.setCreatedAt(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Integer id, Usuario usuario) {
        Usuario usersToUpdate = usuarioRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        if (usuarioRepository.existsByCpfIgnoreCaseAndIdNot(usuario.getCpf(), id) ||
                usuarioRepository.existsByEmailIgnoreCaseAndIdNot(usuario.getEmail(), id)) {
            throw new EntidadeConflitoException();
        }

        if (!isValidEmail(usuario.getEmail())) throw new EntidadeInvalidaException();


        usersToUpdate.setNome(usuario.getNome());
        usersToUpdate.setCpf(usuario.getCpf());
        usersToUpdate.setEmail(usuario.getEmail());
        usersToUpdate.setFkEndereco(usuario.getFkEndereco());
        usersToUpdate.setFkPlano(usuario.getFkPlano());
        usersToUpdate.setUpdatedAt(LocalDateTime.now());

        return usuarioRepository.save(usersToUpdate);
    }

    public Usuario findById(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        Usuario usuario = usuarioRepository.findById(id).orElseThrow((EntidadeNaoEncontradaException::new));

        return usuario;
    }

    public Usuario desativarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        usuario.setStatus(StatusUsuario.INATIVO);
        usuario.setUpdatedAt(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Usuario ativarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        usuario.setStatus(StatusUsuario.ATIVO);
        usuario.setUpdatedAt(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) throw new EntidadeNaoEncontradaException();
        return usuarios;
    }

    public UsuarioTokenDTO autenticar(Usuario usuario) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                usuarioRepository.findByEmail(usuario.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return usuarioMapper.toDtoToken(usuarioAutenticado, token);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
