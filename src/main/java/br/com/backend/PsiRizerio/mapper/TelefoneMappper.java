package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.dto.TelefoneDTO;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface TelefoneMappper {
    Telefone toEntity(TelefoneDTO telefoneDTO);

    TelefoneDTO toDto(Telefone telefone);
}
