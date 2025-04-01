package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoCreateDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco toEntity(EnderecoCreateDTO enderecoCreateDTO);
    Endereco toEntity(EnderecoUpdateDTO enderecoUpdateDTO);
    List<Endereco> toEntityList(List<EnderecoCreateDTO> enderecoCreateDTOs);

    EnderecoCreateDTO toDto(Endereco endereco);
    EnderecoUpdateDTO toDtoUpdate(Endereco endereco);
    EnderecoResponseDTO toDtoResponse(Endereco endereco);
    List<EnderecoResponseDTO> toDtoList(List<Endereco> enderecos);

}
