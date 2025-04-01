package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneCreateDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TelefoneMappper {
    Telefone toEntity(TelefoneCreateDTO telefoneCreateDTO);
    Telefone toEntity(TelefoneUpdateDTO telefoneUpdateDTO);
    List<Telefone> toEntityList(List<TelefoneCreateDTO> telefoneCreateDTOs);

    TelefoneCreateDTO toDto(Telefone telefone);
    TelefoneUpdateDTO toDtoUpdate(Telefone telefone);
    List<Telefone> toDtoList(List<Telefone> telefones);
}
