package br.com.backend.PsiRizerio.preferencia;

import br.com.backend.PsiRizerio.enums.DiaSemana;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.PreferenciaMapper;
import br.com.backend.PsiRizerio.persistence.entities.Preferencia;
import br.com.backend.PsiRizerio.persistence.repositories.PreferenciaRepository;
import br.com.backend.PsiRizerio.service.PreferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PreferenciaTest {

    @Mock
    private PreferenciaRepository preferenciaRepository;

    @Mock
    private PreferenciaMapper preferenciaMapper;

    @InjectMocks
    private PreferenciaService preferenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPreferencia_Conflict() {
        Preferencia preferencia = new Preferencia();
        preferencia.setHorario("10:00");
        preferencia.setDiaSemana(DiaSemana.SEGUNDA);

        when(preferenciaRepository.existsByHorarioAndDiaSemana(preferencia.getHorario(), preferencia.getDiaSemana())).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> preferenciaService.createPreferencia(preferencia));
    }

    @Test
    void updatePreferencia_Success() {
        Preferencia existingPreferencia = new Preferencia();
        existingPreferencia.setId(1);

        Preferencia updatedPreferencia = new Preferencia();
        updatedPreferencia.setHorario("11:00");
        updatedPreferencia.setDiaSemana(DiaSemana.TERCA);

        when(preferenciaRepository.findById(1)).thenReturn(Optional.of(existingPreferencia));
        when(preferenciaRepository.existsByHorarioAndDiaSemanaAndIdNot(updatedPreferencia.getHorario(), updatedPreferencia.getDiaSemana(), 1)).thenReturn(false);
        when(preferenciaRepository.save(existingPreferencia)).thenReturn(existingPreferencia);

        Preferencia result = preferenciaService.updatePreferencia(1, updatedPreferencia);

        assertNotNull(result);
        assertEquals("11:00", result.getHorario());
        assertEquals(DiaSemana.TERCA, result.getDiaSemana());
        verify(preferenciaRepository, times(1)).save(existingPreferencia);
    }

    @Test
    void updatePreferencia_NotFound() {
        Preferencia preferencia = new Preferencia();
        when(preferenciaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> preferenciaService.updatePreferencia(1, preferencia));
    }

    @Test
    void findPreferenciaById_Success() {
        Preferencia preferencia = new Preferencia();
        preferencia.setId(1);

        when(preferenciaRepository.findById(1)).thenReturn(Optional.of(preferencia));

        Preferencia result = preferenciaService.findPreferenciaById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(preferenciaRepository, times(1)).findById(1);
    }

    @Test
    void findPreferenciaById_NotFound() {
        when(preferenciaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> preferenciaService.findPreferenciaById(1));
    }

    @Test
    void findAllPreferencias_NotFound() {
        when(preferenciaRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EntidadeSemConteudoException.class, () -> preferenciaService.findAllPreferencias());
    }

    @Test
    void deletePreferencia_Success() {
        Preferencia preferencia = new Preferencia();
        preferencia.setId(1);

        when(preferenciaRepository.findById(1)).thenReturn(Optional.of(preferencia));
        doNothing().when(preferenciaRepository).delete(preferencia);

        assertDoesNotThrow(() -> preferenciaService.deletePreferencia(1));
        verify(preferenciaRepository, times(1)).delete(preferencia);
    }

    @Test
    void deletePreferencia_NotFound() {
        when(preferenciaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> preferenciaService.deletePreferencia(1));
    }
}