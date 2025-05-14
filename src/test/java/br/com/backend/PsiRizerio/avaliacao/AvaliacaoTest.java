package br.com.backend.PsiRizerio.avaliacao;

import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoRespondeDTO;
import br.com.backend.PsiRizerio.mapper.AvaliacaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Avaliacao;
import br.com.backend.PsiRizerio.persistence.repositories.AvaliacaoRepository;
import br.com.backend.PsiRizerio.service.AvaliacaoService;
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

public class AvaliacaoTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private AvaliacaoMapper avaliacaoMapper;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAvaliacao_Success() {
        AvaliacaoCreateDTO createDTO = new AvaliacaoCreateDTO();
        Avaliacao avaliacao = new Avaliacao();
        Avaliacao savedAvaliacao = new Avaliacao();
        AvaliacaoRespondeDTO responseDTO = new AvaliacaoRespondeDTO();

        when(avaliacaoMapper.toEntity(createDTO)).thenReturn(avaliacao);
        when(avaliacaoRepository.save(avaliacao)).thenReturn(savedAvaliacao);
        when(avaliacaoMapper.toDtoResponse(savedAvaliacao)).thenReturn(responseDTO);

        AvaliacaoRespondeDTO result = avaliacaoService.createAvaliacao(createDTO);

        assertNotNull(result);
        verify(avaliacaoRepository, times(1)).save(avaliacao);
        verify(avaliacaoMapper, times(1)).toDtoResponse(savedAvaliacao);
    }

    @Test
    void getAllAvaliacoes_Success() {
        Avaliacao avaliacao = new Avaliacao();
        AvaliacaoRespondeDTO responseDTO = new AvaliacaoRespondeDTO();

        when(avaliacaoRepository.findAll()).thenReturn(Collections.singletonList(avaliacao));
        when(avaliacaoMapper.toDtoResponse(avaliacao)).thenReturn(responseDTO);

        List<AvaliacaoRespondeDTO> result = avaliacaoService.getAllAvaliacoes();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(avaliacaoRepository, times(1)).findAll();
    }

    @Test
    void getAllAvaliacoes_EmptyList() {
        when(avaliacaoRepository.findAll()).thenReturn(Collections.emptyList());

        List<AvaliacaoRespondeDTO> result = avaliacaoService.getAllAvaliacoes();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(avaliacaoRepository, times(1)).findAll();
    }

    @Test
    void deleteAvaliacaoById_Success() {
        when(avaliacaoRepository.existsById(1)).thenReturn(true);
        doNothing().when(avaliacaoRepository).deleteById(1);

        assertDoesNotThrow(() -> avaliacaoService.deleteAvaliacaoById(1));
        verify(avaliacaoRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteAvaliacaoById_NotFound() {
        when(avaliacaoRepository.existsById(1)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> avaliacaoService.deleteAvaliacaoById(1));
        assertEquals("Avaliacao com o ID 1 não encontrada", exception.getMessage());
        verify(avaliacaoRepository, never()).deleteById(1);
    }
}