package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteTokenDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadePrecondicaoFalhaException;
import br.com.backend.PsiRizerio.mapper.PacienteMapper;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;

import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PacienteService {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final PacienteMapper pacienteMapper;

    public PacienteService(PacienteRepository pacienteRepository, PasswordEncoder passwordEncoder, GerenciadorTokenJwt gerenciadorTokenJwt, AuthenticationManager authenticationManager, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
        this.pacienteMapper = pacienteMapper;
    }

    public Paciente createUser(Paciente paciente) {
        if (pacienteRepository.existsByEmailOrCpfIgnoreCase(paciente.getEmail(), paciente.getCpf())
                && paciente.getStatus() == StatusUsuario.ATIVO) throw new EntidadeConflitoException();

        if (!isValidEmail(paciente.getEmail())) throw new EntidadeInvalidaException();

        String senhaCriptografada = passwordEncoder.encode(paciente.getSenha());
        paciente.setSenha(senhaCriptografada);

        paciente.setCreatedAt(LocalDateTime.now());
        return pacienteRepository.save(paciente);
    }

    public Paciente update(Integer id, Paciente paciente) {
        Paciente usersToUpdate = pacienteRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        if (pacienteRepository.existsByCpfIgnoreCaseAndIdNot(paciente.getCpf(), id) ||
                pacienteRepository.existsByEmailIgnoreCaseAndIdNot(paciente.getEmail(), id)) {
            throw new EntidadeConflitoException();
        }

        if (!isValidEmail(paciente.getEmail())) throw new EntidadeInvalidaException();


        usersToUpdate.setNome(paciente.getNome());
        usersToUpdate.setCpf(paciente.getCpf());
        usersToUpdate.setEmail(paciente.getEmail());
        usersToUpdate.setFkEndereco(paciente.getFkEndereco());
        usersToUpdate.setFkPlano(paciente.getFkPlano());
        usersToUpdate.setUpdatedAt(LocalDateTime.now());

        return pacienteRepository.save(usersToUpdate);
    }

    public Paciente findById(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        Paciente paciente = pacienteRepository.findById(id).orElseThrow((EntidadeNaoEncontradaException::new));

        return paciente;
    }

    public Paciente desativarUsuario(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        paciente.setStatus(StatusUsuario.INATIVO);
        paciente.setUpdatedAt(LocalDateTime.now());
        return pacienteRepository.save(paciente);
    }

    public Paciente ativarUsuario(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        paciente.setStatus(StatusUsuario.ATIVO);
        paciente.setUpdatedAt(LocalDateTime.now());

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> findAll() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        if (pacientes.isEmpty()) throw new EntidadeNaoEncontradaException();
        return pacientes;
    }

    public PacienteTokenDTO autenticar(Paciente paciente) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                paciente.getEmail(), paciente.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Paciente pacienteAutenticado =
                pacienteRepository.findByEmail(paciente.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return pacienteMapper.toDtoToken(pacienteAutenticado, token);
    }

    public void updateSenha(Integer id, String senhaAtual, String novaSenha) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        if (!passwordEncoder.matches(senhaAtual, paciente.getSenha())) {
            throw new EntidadePrecondicaoFalhaException();
        }

        if (senhaAtual.equals(novaSenha)) {
            throw new EntidadeConflitoException();
        }

        String novaSenhaCripto = passwordEncoder.encode(novaSenha);

        paciente.setSenha(novaSenhaCripto);
        pacienteRepository.save(paciente);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
