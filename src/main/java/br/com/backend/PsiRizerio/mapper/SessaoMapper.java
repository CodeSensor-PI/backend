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
        sessao.setDtHrSessao(model.getDtHrSessao());
        sessao.setFkCliente(model.getFkCliente());
        sessao.setTipo(model.getTipo());
        sessao.setStatusSessao(model.getStatusSessao());
        sessao.setAnotacao(model.getAnotacao());
        sessao.setCreatedAt(model.getCreatedAt());
        sessao.setUpdatedAt(model.getUpdatedAt());
        return sessao;
    }

    @Named(value="toDto")
    public SessaoDTO toDto(Sessao entity) {
        SessaoDTO sessaoDTO = new SessaoDTO();
        sessaoDTO.setId(entity.getId());
        sessaoDTO.setDtHrSessao(entity.getDtHrSessao());
        sessaoDTO.setFkCliente(entity.getFkCliente());
        sessaoDTO.setTipo(entity.getTipo());
        sessaoDTO.setStatusSessao(entity.getStatusSessao());
        sessaoDTO.setAnotacao(entity.getAnotacao());
        sessaoDTO.setCreatedAt(entity.getCreatedAt());
        sessaoDTO.setUpdatedAt(entity.getUpdatedAt());
        return sessaoDTO;
    }
}
