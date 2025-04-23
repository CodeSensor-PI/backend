package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.pacienteDTO.*;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    Paciente toEntity(PacienteCreateDTO pacienteCreateDTO);
    Paciente toEntity(PacienteUpdateDTO pacienteUpdateDTO);
    List<Paciente> toEntityList(List<PacienteCreateDTO> pacienteCreateDTOS);
    Paciente toEntity(PacienteLoginDTO pacienteLoginDto);
    Paciente toEntity(PacienteSenhaUpdateDTO pacienteSenhaUpdateDTO);

    PacienteCreateDTO toDto(Paciente paciente);
    PacienteUpdateDTO toDtoUpdate(Paciente paciente);
    PacienteResponseDTO toDtoResponse(Paciente paciente);
    List<PacienteResponseDTO> toDtoList(List<Paciente> pacientes);
    PacienteSenhaUpdateDTO toDtoUpdateSenha(Paciente paciente);
    PacienteTokenDTO toDtoToken(Paciente paciente, String token);


}
