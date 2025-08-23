package br.com.backend.PsiRizerio.plano;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.PlanoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
import br.com.backend.PsiRizerio.persistence.repositories.PlanoRepository;
import br.com.backend.PsiRizerio.service.PlanoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlanoTest {

    @Mock
    private PlanoRepository planoRepository;

    @Mock
    private PlanoMapper planoMapper;

    @InjectMocks
    private PlanoService planoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlano_Success() {
        Plano plano = new Plano();
        when(planoRepository.existsByCategoria(plano.getCategoria())).thenReturn(false);
        when(planoRepository.save(plano)).thenReturn(plano);

        Plano result = planoService.createPlano(plano);

        assertNotNull(result);
        verify(planoRepository, times(1)).save(plano);
    }

    @Test
    void createPlano_Conflict() {
        Plano plano = new Plano();
        when(planoRepository.existsByCategoria(plano.getCategoria())).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> planoService.createPlano(plano));
    }

    @Test
    void updatePlano_Success() {
        Plano plano = new Plano();
        Plano existingPlano = new Plano();
        when(planoRepository.findById(1)).thenReturn(Optional.of(existingPlano));
        when(planoRepository.existsByCategoriaAndIdNot(plano.getCategoria(), 1)).thenReturn(false);
        when(planoRepository.save(existingPlano)).thenReturn(existingPlano);

        Plano result = planoService.update(1, plano);

        assertNotNull(result);
        verify(planoRepository, times(1)).save(existingPlano);
    }

    @Test
    void updatePlano_NotFound() {
        Plano plano = new Plano();
        when(planoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> planoService.update(1, plano));
    }

    @Test
    void updatePlano_Conflict() {
        Plano plano = new Plano();
        Plano existingPlano = new Plano();
        when(planoRepository.findById(1)).thenReturn(Optional.of(existingPlano));
        when(planoRepository.existsByCategoriaAndIdNot(plano.getCategoria(), 1)).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> planoService.update(1, plano));
    }

    @Test
    void findAll_Success() {
        Plano plano = new Plano();
        when(planoRepository.findAll()).thenReturn(Collections.singletonList(plano));

        List<Plano> result = planoService.findAll();

        assertFalse(result.isEmpty());
        verify(planoRepository, times(2)).findAll();
    }

    @Test
    void findAll_NoContent() {
        when(planoRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EntidadeSemConteudoException.class, () -> planoService.findAll());
    }

    @Test
    void findById_Success() {
        Plano plano = new Plano();
        when(planoRepository.findById(1)).thenReturn(Optional.of(plano));

        Plano result = planoService.findById(1);

        assertNotNull(result);
        verify(planoRepository, times(1)).findById(1);
    }

    @Test
    void findById_NotFound() {
        when(planoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> planoService.findById(1));
    }

    @Test
    void delete_Success() {
        when(planoRepository.existsById(1)).thenReturn(true);

        planoService.delete(1);

        verify(planoRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_NotFound() {
        when(planoRepository.existsById(1)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> planoService.delete(1));
    }
}
