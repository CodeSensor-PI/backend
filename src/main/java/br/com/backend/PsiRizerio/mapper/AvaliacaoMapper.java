package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.AvaliacaoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Avaliacao;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {
    Avaliacao toEntity(AvaliacaoDTO avaliacaoDTO);

    AvaliacaoDTO toDto(Avaliacao avaliacao);
}
