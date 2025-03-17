package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.UserDTO;
import br.com.backend.PsiRizerio.persistence.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);

    List<User> toEntity(List<UserDTO> userDTO);

    List<UserDTO> toDto(List<User> user);
}
