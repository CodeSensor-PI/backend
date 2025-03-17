package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    Endereco toEntity(EnderecoDTO enderecoDTO);

    EnderecoDTO toDto(Endereco endereco);
}
