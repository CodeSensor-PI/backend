package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class EnderecoMapper {

    @Named(value = "toEntity")
    public Endereco toEntity(Endereco model) {
        Endereco endereco = new Endereco();
        endereco.setId(model.getId());
        endereco.setCep(model.getCep());
        endereco.setLogradouro(model.getLogradouro());
        endereco.setNumero(model.getNumero());
        endereco.setCidade(model.getCidade());
        endereco.setUf(model.getUf());
        endereco.setCreatedAt(model.getCreatedAt());
        endereco.setUpdatedAt(model.getUpdatedAt());
        return endereco;
    }

    @Named(value = "toModel")
    public Endereco toModel(Endereco entity) {
        Endereco endereco = new Endereco();
        endereco.setId(entity.getId());
        endereco.setCep(entity.getCep());
        endereco.setLogradouro(entity.getLogradouro());
        endereco.setNumero(entity.getNumero());
        endereco.setCidade(entity.getCidade());
        endereco.setUf(entity.getUf());
        endereco.setCreatedAt(entity.getCreatedAt());
        endereco.setUpdatedAt(entity.getUpdatedAt());
        return endereco;
    }


}
