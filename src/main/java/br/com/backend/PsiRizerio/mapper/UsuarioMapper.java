package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.usuarioDTO.*;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioCreateDTO usuarioCreateDTO);
    Usuario toEntity(UsuarioUpdateDTO usuarioUpdateDTO);
    List<Usuario> toEntityList(List<UsuarioCreateDTO> usuarioCreateDTOs);
    Usuario toEntity(UsuarioLoginDTO usuarioLoginDto);
    Usuario toEntity(UsuarioSenhaUpdateDTO usuarioSenhaUpdateDTO);

    UsuarioCreateDTO toDto(Usuario usuario);
    UsuarioUpdateDTO toDtoUpdate(Usuario usuario);
    UsuarioResponseDTO toDtoResponse(Usuario usuario);
    List<UsuarioResponseDTO> toDtoList(List<Usuario> usuarios);
    UsuarioSenhaUpdateDTO toDtoUpdateSenha(Usuario usuario);
    UsuarioTokenDTO toDtoToken(Usuario usuario, String token);


}
