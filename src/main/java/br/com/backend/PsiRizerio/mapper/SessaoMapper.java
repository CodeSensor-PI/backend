package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.SessaoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessaoMapper {
    Sessao toEntity(SessaoDTO sessaoDTO);

    SessaoDTO toDto(Sessao sessao);

    List<SessaoDTO> toDto(List<Sessao> sessoes);

    List<Sessao> toEntity(List<SessaoDTO> sessoes);
}
