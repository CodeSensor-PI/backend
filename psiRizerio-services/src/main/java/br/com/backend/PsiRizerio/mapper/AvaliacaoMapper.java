package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoRespondeDTO;
import br.com.backend.PsiRizerio.dto.avaliacaoDTO.AvaliacaoUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Avaliacao;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {
    Avaliacao toEntity(AvaliacaoCreateDTO avaliacaoDTO);
    Avaliacao toEntity(AvaliacaoUpdateDTO avaliacaoDTO);
    List<Avaliacao> toEntity(List<AvaliacaoCreateDTO> avaliacaoDTO);

    AvaliacaoCreateDTO todtoCreate(Avaliacao avaliacao);
    AvaliacaoUpdateDTO toDtoUpdate(Avaliacao avaliacao);
    AvaliacaoRespondeDTO toDtoResponse(Avaliacao avaliacao);
    List<AvaliacaoCreateDTO> toListDto(List<Avaliacao> avaliacao);
}
