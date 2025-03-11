package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.UserDTO;
import br.com.backend.PsiRizerio.persistence.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public class UserMapper {

    @Named(value="toEntity")
    public User toEntity(UserDTO model) {
        User user = new User();
        user.setId(model.getId());
        user.setName(model.getName());
        user.setEmail(model.getEmail());
        user.setPassword(model.getPassword());
        user.setPhone(model.getPhone());
        user.setAddress(model.getAddress());
        user.setCpf(model.getCpf());
        return user;
    }

    @Named(value="toDto")
    public UserDTO toDto(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setName(entity.getName());
        userDTO.setEmail(entity.getEmail());
        userDTO.setPassword(entity.getPassword());
        userDTO.setPhone(entity.getPhone());
        userDTO.setAddress(entity.getAddress());
        userDTO.setCpf(entity.getCpf());
        return userDTO;
    }

    public List<UserDTO> toDto(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }
}
