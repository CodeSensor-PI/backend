package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteKpiQtdInativoDTO;
import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteTokenDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadePrecondicaoFalhaException;
import br.com.backend.PsiRizerio.mapper.PacienteMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;

import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PacienteService {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final PacienteMapper pacienteMapper;
    private final EnderecoRepository enderecoRepository;

    public Paciente createUser(Paciente paciente) {
        if (pacienteRepository.existsByEmailIgnoreCase(paciente.getEmail())
                && paciente.getStatus() == StatusUsuario.ATIVO) throw new EntidadeConflitoException();

        if (!isValidEmail(paciente.getEmail())) throw new EntidadeInvalidaException();

        if (paciente.getFkEndereco() != null && paciente.getFkEndereco().getId() != null) {
            Integer enderecoId = paciente.getFkEndereco().getId();
            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereço não encontrado com ID: " + enderecoId));
            paciente.setFkEndereco(endereco);
        }

        String senhaCriptografada = passwordEncoder.encode(paciente.getSenha());
        paciente.setSenha(senhaCriptografada);

        paciente.setCreatedAt(LocalDateTime.now());
        return pacienteRepository.save(paciente);
    }

    public Paciente update(Integer id, Paciente paciente) {
        Paciente pacienteToUpdate = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        if (paciente.getCpf() != null && pacienteRepository.existsByCpfIgnoreCaseAndIdNot(paciente.getCpf(), id)) {
            throw new EntidadeConflitoException();
        }

        if (paciente.getEmail() != null) {
            if (pacienteRepository.existsByEmailIgnoreCaseAndIdNot(paciente.getEmail(), id)) {
                throw new EntidadeConflitoException();
            }
            if (!isValidEmail(paciente.getEmail())) {
                throw new EntidadeInvalidaException();
            }
            pacienteToUpdate.setEmail(paciente.getEmail());
        }

        if (paciente.getNome() != null) {
            pacienteToUpdate.setNome(paciente.getNome());
        }

        if (paciente.getCpf() != null) {
            pacienteToUpdate.setCpf(paciente.getCpf());
        }

        if (paciente.getFkEndereco() != null && paciente.getFkEndereco().getId() != null) {
            Integer enderecoId = paciente.getFkEndereco().getId();
            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereço não encontrado com ID: " + enderecoId));
            pacienteToUpdate.setFkEndereco(endereco);
        } else {
            pacienteToUpdate.setFkEndereco(null);
        }

        if (paciente.getFkPlano() != null && paciente.getFkPlano().getId() != null) {
            pacienteToUpdate.setFkPlano(paciente.getFkPlano());
        }

        // Directly update the status field
        pacienteToUpdate.setStatus(paciente.getStatus());

        pacienteToUpdate.setUpdatedAt(LocalDateTime.now());

        return pacienteRepository.save(pacienteToUpdate);
    }

    public Paciente findById(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        Paciente paciente = pacienteRepository.findById(id).orElseThrow((EntidadeNaoEncontradaException::new));

        return paciente;
    }

    public Paciente desativarPaciente(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        paciente.setStatus(StatusUsuario.INATIVO);
        paciente.setUpdatedAt(LocalDateTime.now());
        return pacienteRepository.save(paciente);
    }

    public Paciente ativarPaciente(Integer id) {
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
                                () -> new ResponseStatusException(404, "Email do paciente não cadastrado", null)
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

    public Paciente addDadosPrimeiroLogin(Integer id, Paciente paciente) {
        Paciente pacienteToUpdate = pacienteRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        if (pacienteRepository.existsByCpf(paciente.getCpf())) throw new EntidadeConflitoException();

        if (paciente.getFkEndereco() == null && paciente.getFkEndereco().getId() == null)
            throw new EntidadeInvalidaException();

        pacienteToUpdate.setDataNasc(paciente.getDataNasc());
        pacienteToUpdate.setFkEndereco(paciente.getFkEndereco());
        pacienteToUpdate.setCpf(paciente.getCpf());
        pacienteToUpdate.setMotivoConsulta(paciente.getMotivoConsulta());
        pacienteToUpdate.setUpdatedAt(LocalDateTime.now());

        return pacienteRepository.save(pacienteToUpdate);
    }

    public PacienteKpiQtdInativoDTO getQtdInativosKpi() {
        Double percentual = pacienteRepository.getPercentualInativos();
        return new PacienteKpiQtdInativoDTO(percentual);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
