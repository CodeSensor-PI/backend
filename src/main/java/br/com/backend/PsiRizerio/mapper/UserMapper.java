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
        user.setNome(model.getNome());
        user.setCpf(model.getCpf());
        user.setEmail(model.getEmail());
        user.setSenha(model.getSenha());
        user.setFk_plano(model.getFkPlano());
        user.setFk_endereco(model.getFkEndereco());
        user.setCreatedAt(model.getCreatedAt());
        user.setUpdatedAt(model.getUpdatedAt());
        return user;
    }

    @Named(value="toDto")
    public UserDTO toDto(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setNome(entity.getNome());
        userDTO.setCpf(entity.getCpf());
        userDTO.setEmail(entity.getEmail());
        userDTO.setSenha(entity.getSenha());
        userDTO.setFkPlano(entity.getFk_plano());
        userDTO.setFkEndereco(entity.getFk_endereco());
        userDTO.setCreatedAt(entity.getCreatedAt());
        userDTO.setUpdatedAt(entity.getUpdatedAt());
        return userDTO;
    }

    public List<UserDTO> toDto(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }
}
