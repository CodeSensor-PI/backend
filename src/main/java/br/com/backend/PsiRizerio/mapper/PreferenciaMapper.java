package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaCreateDTO;
import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaResponseDTO;
import br.com.backend.PsiRizerio.dto.preferenciaDTO.PreferenciaUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Preferencia;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PreferenciaMapper {
    Preferencia toEntity(PreferenciaCreateDTO preferenciaCreateDTO);
    Preferencia toEntity(PreferenciaUpdateDTO preferenciaUpdateDTO);
    List<Preferencia> toEntityList(List<PreferenciaCreateDTO> preferenciaCreateDTO);

    PreferenciaUpdateDTO toDtoUpdate(Preferencia preferencia);
    PreferenciaCreateDTO toDtoCreate(Preferencia preferencia);
    PreferenciaResponseDTO toDtoResponse(Preferencia preferencia);
    List<PreferenciaResponseDTO> toDtoList(List<Preferencia> preferencia);

}
