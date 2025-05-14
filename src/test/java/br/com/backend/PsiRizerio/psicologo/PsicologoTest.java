package br.com.backend.PsiRizerio.psicologo;

import br.com.backend.PsiRizerio.dto.psicologoDTO.PsicologoTokenDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import br.com.backend.PsiRizerio.exception.*;
import br.com.backend.PsiRizerio.mapper.PsicologoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import br.com.backend.PsiRizerio.persistence.repositories.PsicologoRepository;
import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import br.com.backend.PsiRizerio.service.PsicologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PsicologoTest {

    @Mock
    private PsicologoRepository psicologoRepository;

    @Mock
    private PsicologoMapper psicologoMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private PsicologoService psicologoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPsicologo_Success() {
        Psicologo psicologo = new Psicologo();
        psicologo.setEmail("test@example.com");
        psicologo.setSenha("password");
        psicologo.setStatus(StatusUsuario.ATIVO);

        when(psicologoRepository.existsByEmailOrCrpIgnoreCase(psicologo.getEmail(), psicologo.getCrp())).thenReturn(false);
        when(passwordEncoder.encode(psicologo.getSenha())).thenReturn("encodedPassword");
        when(psicologoRepository.save(psicologo)).thenReturn(psicologo);

        Psicologo result = psicologoService.createPsicologo(psicologo);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getSenha());
        verify(psicologoRepository, times(1)).save(psicologo);
    }

    @Test
    void createPsicologo_Conflict() {
        Psicologo psicologo = new Psicologo();
        psicologo.setEmail("test@example.com");
        psicologo.setCrp("1234567");
        psicologo.setStatus(StatusUsuario.ATIVO);

        when(psicologoRepository.existsByEmailOrCrpIgnoreCase(psicologo.getEmail(), psicologo.getCrp())).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> psicologoService.createPsicologo(psicologo));
    }

    @Test
    void updatePsicologo_Success() {
        Psicologo existingPsicologo = new Psicologo();
        existingPsicologo.setId(1);

        Psicologo updatedPsicologo = new Psicologo();
        updatedPsicologo.setEmail("updated@example.com");

        when(psicologoRepository.findById(1)).thenReturn(Optional.of(existingPsicologo));
        when(psicologoRepository.existsByEmailIgnoreCaseAndIdNot(updatedPsicologo.getEmail(), 1)).thenReturn(false);
        when(psicologoRepository.save(existingPsicologo)).thenReturn(existingPsicologo);

        Psicologo result = psicologoService.update(1, updatedPsicologo);

        assertNotNull(result);
        assertEquals("updated@example.com", existingPsicologo.getEmail());
        verify(psicologoRepository, times(1)).save(existingPsicologo);
    }

    @Test
    void updatePsicologo_NotFound() {
        Psicologo psicologo = new Psicologo();
        when(psicologoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> psicologoService.update(1, psicologo));
    }

    @Test
    void findById_Success() {
        Psicologo psicologo = new Psicologo();
        psicologo.setId(1);

        when(psicologoRepository.findById(1)).thenReturn(Optional.of(psicologo));

        Psicologo result = psicologoService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(psicologoRepository, times(1)).findById(1);
    }

    @Test
    void findById_NotFound() {
        when(psicologoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> psicologoService.findById(1));
    }

    @Test
    void findAll_Success() {
        Psicologo psicologo = new Psicologo();
        when(psicologoRepository.findAll()).thenReturn(Collections.singletonList(psicologo));

        List<Psicologo> result = psicologoService.findAll();

        assertFalse(result.isEmpty());
        verify(psicologoRepository, times(1)).findAll();
    }

    @Test
    void findAll_NotFound() {
        when(psicologoRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EntidadeNaoEncontradaException.class, () -> psicologoService.findAll());
    }

    @Test
    void desativarPsicologo_Success() {
        Psicologo psicologo = new Psicologo();
        psicologo.setId(1);
        psicologo.setStatus(StatusUsuario.ATIVO);

        when(psicologoRepository.findById(1)).thenReturn(Optional.of(psicologo));
        when(psicologoRepository.save(psicologo)).thenReturn(psicologo);

        Psicologo result = psicologoService.desativarPsicologo(1);

        assertNotNull(result);
        assertEquals(StatusUsuario.INATIVO, result.getStatus());
        verify(psicologoRepository, times(1)).save(psicologo);
    }
}