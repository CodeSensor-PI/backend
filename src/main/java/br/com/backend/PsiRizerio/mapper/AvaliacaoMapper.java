package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.persistence.entities.Avaliacao;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class AvaliacaoMapper {

    @Named(value = "toEntity")
    public Avaliacao toEntity(Avaliacao model) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(model.getId());
        avaliacao.setNota(model.getNota());
        avaliacao.setComentario(model.getComentario());
        return avaliacao;
    }

    @Named(value = "toModel")
    public Avaliacao toModel(Avaliacao entity) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(entity.getId());
        avaliacao.setNota(entity.getNota());
        avaliacao.setComentario(entity.getComentario());
        return avaliacao;
    }
}
