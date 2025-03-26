package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.planoDTO.PlanoCreateDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanoMapper {
    Plano toEntity(PlanoCreateDTO planoCreateDTO);
    Plano toEntity(PlanoUpdateDTO planoUpdateDTO);
    List<Plano> toEntityList(List<PlanoCreateDTO> planoCreateDTOs);

    PlanoCreateDTO toDto(Plano plano);
    PlanoUpdateDTO toDtoUpdate(Plano plano);
    PlanoResponseDTO toDtoResponse(Plano plano);
    List<PlanoResponseDTO> toDtoList(List<Plano> planos);

}
