package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.psicologoDTO.PsicologoCreateDTO;
import br.com.backend.PsiRizerio.dto.psicologoDTO.PsicologoResponseDTO;
import br.com.backend.PsiRizerio.dto.psicologoDTO.PsicologoUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PsicologoMapper {
    Psicologo toEntity(PsicologoCreateDTO psicologoCreateDTO);
    Psicologo toEntity(PsicologoUpdateDTO psicologoUpdateDTO);
    List<Psicologo> toEntityList(List<PsicologoCreateDTO> psicologoCreateDTO);

    PsicologoCreateDTO toDtoCreate(Psicologo psicologo);
    PsicologoUpdateDTO toDtoUpdate(Psicologo psicologo);
    PsicologoResponseDTO toDtoResponse(Psicologo psicologo);
    List<PsicologoCreateDTO> toDtoList(List<Psicologo> psicologos);
}
