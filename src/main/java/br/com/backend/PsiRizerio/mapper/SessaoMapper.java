package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.SessaoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class SessaoMapper {

    @Named(value="toEntity")
    public Sessao toEntity(SessaoDTO model) {
        Sessao sessao = new Sessao();
        sessao.setId(model.getId());
        sessao.setData(model.getData());
        sessao.setTitle(model.getTitle());
        sessao.setDescription(model.getDescription());
        sessao.setStart_time(model.getStart_time());
        sessao.setEnd_time(model.getEnd_time());
        sessao.setUserId(model.getUserId());
        return sessao;
    }

    @Named(value="toDto")
    public SessaoDTO toDto(Sessao entity) {
        SessaoDTO sessaoDTO = new SessaoDTO();
        sessaoDTO.setId(entity.getId());
        sessaoDTO.setData(entity.getData());
        sessaoDTO.setTitle(entity.getTitle());
        sessaoDTO.setDescription(entity.getDescription());
        sessaoDTO.setStart_time(entity.getStart_time());
        sessaoDTO.setEnd_time(entity.getEnd_time());
        sessaoDTO.setUserId(entity.getUserId());
        return sessaoDTO;
    }
}
