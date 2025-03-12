package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.TelefoneDTO;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class TelefoneMappper {
    @Named(value = "toEntity")
    public Telefone toEntity(TelefoneDTO model) {
        Telefone telefone = new Telefone();
        telefone.setId(model.getId());
        telefone.setDdd(model.getDdd());
        telefone.setNumero(model.getNumero());
        telefone.setTipo(model.getTipo());
        telefone.setCreatedAt(model.getCreatedAt());
        telefone.setUpdatedAt(model.getUpdatedAt());
        return telefone;
    }

    @Named(value = "toDto")
    public TelefoneDTO toDto(Telefone entity) {
        TelefoneDTO telefoneDTO = new TelefoneDTO();
        telefoneDTO.setId(entity.getId());
        telefoneDTO.setDdd(entity.getDdd());
        telefoneDTO.setNumero(entity.getNumero());
        telefoneDTO.setTipo(entity.getTipo());
        telefoneDTO.setCreatedAt(entity.getCreatedAt());
        telefoneDTO.setUpdatedAt(entity.getUpdatedAt());
        return telefoneDTO;
    }
}
