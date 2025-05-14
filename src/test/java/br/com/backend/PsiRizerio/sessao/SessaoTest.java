package br.com.backend.PsiRizerio.sessao;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import br.com.backend.PsiRizerio.persistence.repositories.SessaoRepository;
import br.com.backend.PsiRizerio.service.SessaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessaoTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private SessaoService sessaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSessao_Success() {
        Paciente paciente = new Paciente();
        paciente.setId(1);

        Sessao sessao = new Sessao();
        sessao.setData(LocalDate.now());
        sessao.setHora(LocalTime.now());
        sessao.setFkPaciente(paciente);

        when(sessaoRepository.existsByDataAndHora(sessao.getData(), sessao.getHora())).thenReturn(false);
        when(sessaoRepository.existsByDataAndHoraBetween(sessao.getData(), sessao.getHora(), sessao.getHora().plusHours(1))).thenReturn(false);
        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));
        when(sessaoRepository.save(sessao)).thenReturn(sessao);

        Sessao result = sessaoService.createSessao(sessao);

        assertNotNull(result);
        assertEquals(paciente, result.getFkPaciente());
        verify(sessaoRepository, times(1)).save(sessao);
    }

    @Test
    void createSessao_Conflict() {
        Sessao sessao = new Sessao();
        sessao.setData(LocalDate.now());
        sessao.setHora(LocalTime.now());

        when(sessaoRepository.existsByDataAndHora(sessao.getData(), sessao.getHora())).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> sessaoService.createSessao(sessao));
    }

    @Test
    void update_Success() {
        Sessao existingSessao = new Sessao();
        existingSessao.setId(1);

        Sessao updatedSessao = new Sessao();
        updatedSessao.setData(LocalDate.now());
        updatedSessao.setHora(LocalTime.now());

        when(sessaoRepository.findById(1)).thenReturn(Optional.of(existingSessao));
        when(sessaoRepository.existsByDataAndHoraBetweenAndIdNot(updatedSessao.getData(), updatedSessao.getHora(), updatedSessao.getHora().plusHours(1), 1)).thenReturn(false);
        when(sessaoRepository.save(existingSessao)).thenReturn(existingSessao);

        Sessao result = sessaoService.update(1, updatedSessao);

        assertNotNull(result);
        assertEquals(updatedSessao.getData(), result.getData());
        assertEquals(updatedSessao.getHora(), result.getHora());
        verify(sessaoRepository, times(1)).save(existingSessao);
    }

    @Test
    void update_NotFound() {
        Sessao sessao = new Sessao();
        when(sessaoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeConflitoException.class, () -> sessaoService.update(1, sessao));
    }

    @Test
    void findAll_NotFound() {
        when(sessaoRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EntidadeNaoEncontradaException.class, () -> sessaoService.findAll());
    }

    @Test
    void findById_Success() {
        Sessao sessao = new Sessao();
        sessao.setId(1);

        when(sessaoRepository.findById(1)).thenReturn(Optional.of(sessao));

        Sessao result = sessaoService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(sessaoRepository, times(1)).findById(1);
    }

    @Test
    void findById_NotFound() {
        when(sessaoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> sessaoService.findById(1));
    }

    @Test
    void delete_Success() {
        when(sessaoRepository.existsById(1)).thenReturn(true);
        doNothing().when(sessaoRepository).deleteById(1);

        assertDoesNotThrow(() -> sessaoService.delete(1));
        verify(sessaoRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_NotFound() {
        when(sessaoRepository.existsById(1)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> sessaoService.delete(1));
    }

    @Test
    void findByPacienteId_Success() {
        Paciente paciente = new Paciente();
        paciente.setId(1);

        Sessao sessao = new Sessao();
        sessao.setFkPaciente(paciente);

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));
        when(sessaoRepository.findByFkPaciente(paciente)).thenReturn(List.of(sessao));

        List<Sessao> result = sessaoService.findByPacienteId(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(sessaoRepository, times(1)).findByFkPaciente(paciente);
    }

    @Test
    void findByPacienteId_NotFound() {
        when(pacienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> sessaoService.findByPacienteId(1));
    }
}