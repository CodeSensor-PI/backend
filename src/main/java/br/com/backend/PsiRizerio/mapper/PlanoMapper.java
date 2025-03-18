package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.PlanoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanoMapper {

    Plano toEntity(PlanoDTO planoDTO);

    PlanoDTO toDto(Plano plano);

    List<PlanoDTO> toDto(List<Plano> plano);

    List<Plano> toEntity(List<PlanoDTO> planoDTO);

}
