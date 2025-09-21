package br.com.backend.PsiRizerio.paciente;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteTokenDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import br.com.backend.PsiRizerio.exception.*;
import br.com.backend.PsiRizerio.mapper.PacienteMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import br.com.backend.PsiRizerio.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PacienteTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PacienteMapper pacienteMapper;

    @InjectMocks
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_Success() {
        Paciente paciente = new Paciente();
        paciente.setEmail("test@example.com");
        paciente.setStatus(StatusUsuario.ATIVO);

        when(pacienteRepository.existsByEmailIgnoreCase(paciente.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Paciente result = pacienteService.createUser(paciente);

        assertNotNull(result);
        assertNotNull(result.getSenha());
        assertEquals("encodedPassword", result.getSenha());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void createUser_Conflict() {
        Paciente paciente = new Paciente();
        paciente.setEmail("test@example.com");
        paciente.setStatus(StatusUsuario.ATIVO);

        when(pacienteRepository.existsByEmailIgnoreCase(paciente.getEmail())).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> pacienteService.createUser(paciente));
    }

    @Test
    void update_Success() {
        Paciente existingPaciente = new Paciente();
        existingPaciente.setId(1);

        Paciente updatedPaciente = new Paciente();
        updatedPaciente.setEmail("updated@example.com");

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(existingPaciente));
        when(pacienteRepository.save(existingPaciente)).thenReturn(existingPaciente);

        Paciente result = pacienteService.update(1, updatedPaciente);

        assertNotNull(result);
        assertEquals("updated@example.com", existingPaciente.getEmail());
        verify(pacienteRepository, times(1)).save(existingPaciente);
    }

    @Test
    void update_NotFound() {
        Paciente paciente = new Paciente();
        when(pacienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> pacienteService.update(1, paciente));
    }

    @Test
    void findById_Success() {
        Paciente paciente = new Paciente();
        paciente.setId(1);

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));

        Paciente result = pacienteService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(pacienteRepository, times(1)).findById(1);
    }

    @Test
    void findById_NotFound() {
        when(pacienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> pacienteService.findById(1));
    }

    @Test
    void findAll_Success() {
        Paciente paciente = new Paciente();
        Page<Paciente> pacientePage = new org.springframework.data.domain.PageImpl<>(Collections.singletonList(paciente));
        when(pacienteRepository.findAll(any(org.springframework.data.domain.Pageable.class))).thenReturn(pacientePage);

        Page<Paciente> result = pacienteService.findAll(org.springframework.data.domain.PageRequest.of(0, 10));

        assertFalse(result.isEmpty());
        verify(pacienteRepository, times(1)).findAll(any(org.springframework.data.domain.Pageable.class));
    }

    @Test
    void findAll_NotFound() {
        Page<Paciente> pacientePage = new org.springframework.data.domain.PageImpl<>(Collections.emptyList());
        when(pacienteRepository.findAll(any(org.springframework.data.domain.Pageable.class))).thenReturn(pacientePage);

        Page<Paciente> result = pacienteService.findAll(org.springframework.data.domain.PageRequest.of(0, 10));
        assertTrue(result.isEmpty());
        verify(pacienteRepository, times(1)).findAll(any(org.springframework.data.domain.Pageable.class));
    }

    @Test
    void desativarPaciente_Success() {
        Paciente paciente = new Paciente();
        paciente.setId(1);
        paciente.setStatus(StatusUsuario.ATIVO);

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        Paciente result = pacienteService.desativarPaciente(1);

        assertNotNull(result);
        assertEquals(StatusUsuario.INATIVO, result.getStatus());
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    void autenticar_Failure() {
        Paciente paciente = new Paciente();
        paciente.setEmail("test@example.com");
        paciente.setSenha("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> pacienteService.autenticar(paciente));
    }
}