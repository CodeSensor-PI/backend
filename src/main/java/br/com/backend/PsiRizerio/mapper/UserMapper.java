package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.dto.userDTO.UsuarioCreateDTO;
import br.com.backend.PsiRizerio.dto.userDTO.UsuarioResponseDTO;
import br.com.backend.PsiRizerio.dto.userDTO.UsuarioUpdateDTO;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UsuarioCreateDTO usuarioResponseDTO);

    UsuarioCreateDTO toDto(User user);

    List<User> toEntity(List<UsuarioResponseDTO> usuarioResponseDTO);

    List<UsuarioResponseDTO> toDtoList(List<User> user);

    User toEntity(UsuarioUpdateDTO usuarioUpdateDTO);

    UsuarioUpdateDTO toDtoUpdate(User user);

    UsuarioResponseDTO toDtoResponse(User user);
}
