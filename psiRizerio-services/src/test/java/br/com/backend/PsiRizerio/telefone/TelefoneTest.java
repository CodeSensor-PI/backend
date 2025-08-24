package br.com.backend.PsiRizerio.telefone;

import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import br.com.backend.PsiRizerio.persistence.repositories.TelefoneRepository;
import br.com.backend.PsiRizerio.service.TelefoneService;
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

public class TelefoneTest {

    @Mock
    private TelefoneRepository telefoneRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private TelefoneService telefoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTelefone_Success() {
        Paciente paciente = new Paciente();
        paciente.setId(1);

        Telefone telefone = new Telefone();
        telefone.setFkPaciente(paciente);

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(telefone);

        Telefone createdTelefone = telefoneService.createTelefone(telefone);

        assertNotNull(createdTelefone);
        assertEquals(paciente, createdTelefone.getFkPaciente());
        verify(telefoneRepository, times(1)).save(telefone);
    }

    @Test
    void createTelefone_PacienteNotFound() {
        Telefone telefone = new Telefone();
        Paciente paciente = new Paciente();
        paciente.setId(1);
        telefone.setFkPaciente(paciente);

        when(pacienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> telefoneService.createTelefone(telefone));
        verify(telefoneRepository, never()).save(any(Telefone.class));
    }

    @Test
    void updateTelefone_Success() {
        Telefone existingTelefone = new Telefone();
        existingTelefone.setId(1);
        existingTelefone.setDdd("11");
        existingTelefone.setNumero("123456789");

        Telefone updatedTelefone = new Telefone();
        updatedTelefone.setDdd("21");
        updatedTelefone.setNumero("987654321");

        when(telefoneRepository.findById(1)).thenReturn(Optional.of(existingTelefone));
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(existingTelefone);

        Telefone result = telefoneService.update(1, updatedTelefone);

        assertNotNull(result);
        assertEquals("21", result.getDdd());
        assertEquals("987654321", result.getNumero());
        verify(telefoneRepository, times(1)).save(existingTelefone);
    }

    @Test
    void updateTelefone_NotFound() {
        Telefone telefone = new Telefone();
        when(telefoneRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> telefoneService.update(1, telefone));
        verify(telefoneRepository, never()).save(any(Telefone.class));
    }

    @Test
    void findById_Success() {
        Telefone telefone = new Telefone();
        telefone.setId(1);

        when(telefoneRepository.findById(1)).thenReturn(Optional.of(telefone));

        Telefone result = telefoneService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(telefoneRepository, times(1)).findById(1);
    }

    @Test
    void findById_NotFound() {
        when(telefoneRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> telefoneService.findById(1));
        verify(telefoneRepository, times(1)).findById(1);
    }

    @Test
    void findByFkPaciente_Success() {
        Telefone telefone = new Telefone();
        Paciente paciente = new Paciente();
        paciente.setId(1);
        telefone.setFkPaciente(paciente);

        when(telefoneRepository.findByFkPaciente(1)).thenReturn(List.of(telefone));

        List<Telefone> result = telefoneService.findByFkPaciente(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(telefoneRepository, times(1)).findByFkPaciente(1);
    }

    @Test
    void findByFkPaciente_NotFound() {
        when(telefoneRepository.findByFkPaciente(1)).thenReturn(Collections.emptyList());

        assertThrows(EntidadeNaoEncontradaException.class, () -> telefoneService.findByFkPaciente(1));
        verify(telefoneRepository, times(1)).findByFkPaciente(1);
    }

    @Test
    void deleteTelefone_Success() {
        Telefone telefone = new Telefone();
        telefone.setId(1);

        when(telefoneRepository.findById(1)).thenReturn(Optional.of(telefone));
        doNothing().when(telefoneRepository).delete(telefone);

        assertDoesNotThrow(() -> telefoneService.delete(1));
        verify(telefoneRepository, times(1)).delete(telefone);
    }

    @Test
    void deleteTelefone_NotFound() {
        when(telefoneRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> telefoneService.delete(1));
        verify(telefoneRepository, never()).delete(any(Telefone.class));
    }
}