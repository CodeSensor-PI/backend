package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneCreateDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneResponseDTO;
import br.com.backend.PsiRizerio.dto.telefoneDTO.TelefoneUpdateDTO;
import br.com.backend.PsiRizerio.dto.usuarioDTO.UsuarioSessaoResponseDTO;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TelefoneMapper {
    Telefone toEntity(TelefoneUpdateDTO telefoneUpdateDTO);
    List<Telefone> toEntityList(List<TelefoneCreateDTO> telefoneCreateDTOs);

    TelefoneCreateDTO toDto(Telefone telefone);
    TelefoneUpdateDTO toDtoUpdate(Telefone telefone);
    List<TelefoneResponseDTO> toDtoList(List<Telefone> telefones);

    @Mapping(target = "fkCliente", source = "fkCliente")
    Telefone toEntity(TelefoneCreateDTO telefoneCreateDTO);

    @Mapping(target = "fkCliente", source = "fkCliente")
    TelefoneResponseDTO toResponseDTO(Telefone entity);

    // Método de mapeamento personalizado
    Usuario map(UsuarioSessaoResponseDTO value);
}
