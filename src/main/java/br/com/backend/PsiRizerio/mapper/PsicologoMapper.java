package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.psicologoDTO.*;
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
    List<PsicologoResponseDTO> toDtoList(List<Psicologo> psicologos);
    PsicologoTokenDTO toDtoToken(Psicologo psicologo, String token);

    Psicologo toEntity(PsicologoLoginDTO psicologoLoginDTO);
}
