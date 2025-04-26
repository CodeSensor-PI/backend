package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneCreateDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneResponseDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TelefoneMapper {
    Telefone toEntity(TelefoneCreateDTO telefoneCreateDTO);
    Telefone toEntity(TelefoneUpdateDTO telefoneUpdateDTO);
    List<Telefone> toEntityList(List<TelefoneCreateDTO> telefoneCreateDTOs);

    TelefoneCreateDTO toDto(Telefone telefone);
    TelefoneUpdateDTO toDtoUpdate(Telefone telefone);
    TelefoneResponseDTO toDtoResponse(Telefone telefone);
    List<TelefoneResponseDTO> toDtoList(List<Telefone> telefones);
}
