package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.preferenciaConsultaDTO.PrefenciaConsultaResponseDTO;
import br.com.backend.PsiRizerio.dto.preferenciaConsultaDTO.PreferenciaConsultaCreateDTO;
import br.com.backend.PsiRizerio.dto.preferenciaConsultaDTO.PreferenciaConsultaUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.PreferenciaConsulta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PreferenciaConsultaMapper {
    PreferenciaConsulta toEntity(PreferenciaConsultaCreateDTO preferenciaConsultaCreateDTO);
    PreferenciaConsulta toEntity(PreferenciaConsultaUpdateDTO preferenciaConsultaUpdateDTO);
    List<PreferenciaConsulta> toEntityList(List<PreferenciaConsultaCreateDTO> preferenciaConsultaCreateDTOs);

    PreferenciaConsultaUpdateDTO toDtoUpdate(PreferenciaConsulta preferenciaConsulta);
    PreferenciaConsultaCreateDTO toDtoCreate(PreferenciaConsulta preferenciaConsulta);
    PrefenciaConsultaResponseDTO toDtoResponse(PreferenciaConsulta preferenciaConsulta);
    List<PrefenciaConsultaResponseDTO> toDtoList(List<PreferenciaConsulta> preferenciaConsultas);

}
