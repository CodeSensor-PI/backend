package br.com.backend.PsiRizerio.autenticacao;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteDetalhesDTO;
import br.com.backend.PsiRizerio.dto.psicologoDTO.PsicologoDetalhesDTO;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PsicologoRepository;
import br.com.backend.PsiRizerio.service.AutenticacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutenticacaoTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PsicologoRepository psicologoRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_PacienteFound() {
        Paciente paciente = new Paciente();
        paciente.setEmail("paciente@example.com");
        paciente.setSenha("senha123");

        when(pacienteRepository.findByEmail("paciente@example.com")).thenReturn(Optional.of(paciente));

        PacienteDetalhesDTO result = (PacienteDetalhesDTO) autenticacaoService.loadUserByUsername("paciente@example.com");

        assertNotNull(result);
        assertEquals("paciente@example.com", result.getUsername());
        verify(pacienteRepository, times(1)).findByEmail("paciente@example.com");
        verify(psicologoRepository, never()).findByEmail(anyString());
    }

    @Test
    void loadUserByUsername_PsicologoFound() {
        Psicologo psicologo = new Psicologo();
        psicologo.setEmail("psicologo@example.com");
        psicologo.setSenha("senha456");
        psicologo.setNome("Dr. Psicologo");

        when(pacienteRepository.findByEmail("psicologo@example.com")).thenReturn(Optional.empty());
        when(psicologoRepository.findByEmail("psicologo@example.com")).thenReturn(Optional.of(psicologo));

        PsicologoDetalhesDTO result = (PsicologoDetalhesDTO) autenticacaoService.loadUserByUsername("psicologo@example.com");

        assertNotNull(result);
        assertEquals("psicologo@example.com", result.getUsername());
        assertEquals("Dr. Psicologo", result.getNome());
        verify(pacienteRepository, times(1)).findByEmail("psicologo@example.com");
        verify(psicologoRepository, times(1)).findByEmail("psicologo@example.com");
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        when(pacienteRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());
        when(psicologoRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                autenticacaoService.loadUserByUsername("notfound@example.com")
        );

        assertEquals("Usuário com email notfound@example.com não encontrado", exception.getMessage());
        verify(pacienteRepository, times(1)).findByEmail("notfound@example.com");
        verify(psicologoRepository, times(1)).findByEmail("notfound@example.com");
    }
}