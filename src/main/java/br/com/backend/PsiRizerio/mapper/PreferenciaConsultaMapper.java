package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.PreferenciaConsultaDTO;
import br.com.backend.PsiRizerio.persistence.entities.PreferenciaConsulta;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class PreferenciaConsultaMapper {

    @Named(value = "toEntity")
    public PreferenciaConsulta toEntity(PreferenciaConsultaDTO model) {
        PreferenciaConsulta preferenciaConsulta = new PreferenciaConsulta();
        preferenciaConsulta.setId(model.getId());
        preferenciaConsulta.setFrequencia(model.getFrequencia());
        preferenciaConsulta.setDiaSemana(model.getDiaSemana());
        preferenciaConsulta.setHorario(model.getHorario());
        preferenciaConsulta.setPlataformaAtendimento(model.getPlataformaAtendimento());
        preferenciaConsulta.setFk_user(model.getFkUser());
        preferenciaConsulta.setCreatedAt(model.getCreatedAt());
        preferenciaConsulta.setUpdatedAt(model.getUpdatedAt());
        return preferenciaConsulta;
    }

    @Named(value = "toDto")
    public PreferenciaConsultaDTO toDto(PreferenciaConsulta entity) {
        PreferenciaConsultaDTO preferenciaConsultaDTO = new PreferenciaConsultaDTO();
        preferenciaConsultaDTO.setId(entity.getId());
        preferenciaConsultaDTO.setFrequencia(entity.getFrequencia());
        preferenciaConsultaDTO.setDiaSemana(entity.getDiaSemana());
        preferenciaConsultaDTO.setHorario(entity.getHorario());
        preferenciaConsultaDTO.setPlataformaAtendimento(entity.getPlataformaAtendimento());
        preferenciaConsultaDTO.setFkUser(entity.getFk_user());
        preferenciaConsultaDTO.setCreatedAt(entity.getCreatedAt());
        preferenciaConsultaDTO.setUpdatedAt(entity.getUpdatedAt());
        return preferenciaConsultaDTO;
    }
}
