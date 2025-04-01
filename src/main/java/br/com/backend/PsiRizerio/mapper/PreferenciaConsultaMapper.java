package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.PreferenciaConsultaDTO;
import br.com.backend.PsiRizerio.persistence.entities.PreferenciaConsulta;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PreferenciaConsultaMapper {
    PreferenciaConsulta toEntity(PreferenciaConsultaDTO preferenciaConsultaDTO);

    PreferenciaConsultaDTO toDto(PreferenciaConsulta preferenciaConsulta);

}
