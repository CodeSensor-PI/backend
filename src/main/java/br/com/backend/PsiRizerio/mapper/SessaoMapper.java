package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoCreateDTO;
import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoResponseDTO;
import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessaoMapper {
    Sessao toEntity(SessaoCreateDTO sessaoCreateDTO);
    Sessao toEntity(SessaoUpdateDTO sessaoUpdateDTO);
    List<Sessao> toEntityList(List<SessaoCreateDTO> sessaoCreateDTOs);

    SessaoCreateDTO toDto(Sessao sessao);
    SessaoUpdateDTO toDtoUpdate(Sessao sessao);
    SessaoResponseDTO toDtoResponse(Sessao sessao);
    List<SessaoResponseDTO> toDtoList(List<Sessao> sessoes);
}
