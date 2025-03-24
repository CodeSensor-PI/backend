package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco toEntity(EnderecoDTO enderecoDTO);

    EnderecoDTO toDto(Endereco endereco);

    List<EnderecoDTO> toDto(List<Endereco> enderecos);

    List<Endereco> toEntity(List<EnderecoDTO> enderecoDTOS);

}
