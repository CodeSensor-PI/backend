package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoRespondeDTO;
import br.com.backend.PsiRizerio.mapper.AvaliacaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Avaliacao;
import br.com.backend.PsiRizerio.persistence.repositories.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoRespondeDTO createAvaliacao(AvaliacaoCreateDTO avaliacaoCreateDTO) {
        Avaliacao avaliacao = avaliacaoMapper.toEntity(avaliacaoCreateDTO);
        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);
        return avaliacaoMapper.toDtoResponse(savedAvaliacao);
    }

    public List<AvaliacaoRespondeDTO> getAllAvaliacoes() {
        return avaliacaoRepository.findAll()
                .stream()
                .map(avaliacaoMapper::toDtoResponse)
                .collect(Collectors.toList());
    }

    public void deleteAvaliacaoById(Integer id) {
        if (avaliacaoRepository.existsById(id)) {
            avaliacaoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Avaliacao com o ID " + id + " não encontrada");
        }
    }
}