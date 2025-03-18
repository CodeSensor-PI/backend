package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class EnderecoService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    public EnderecoDTO createEndereco(EnderecoDTO enderecoDTO) {
        if (enderecoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço inválido");
        }

        try {
            return enderecoMapper.toDto(enderecoRepository.save(enderecoMapper.toEntity(enderecoDTO)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar endereço: " + e.getMessage());
        }

    }

    public EnderecoDTO update(Integer id, EnderecoDTO enderecoDTO) {
        if (enderecoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço inválido");
        }

        try {
            var enderecoToUpdate = enderecoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado"));
            enderecoToUpdate.setCep(enderecoDTO.getCep());
            enderecoToUpdate.setCidade(enderecoDTO.getCidade());
            enderecoToUpdate.setLogradouro(enderecoDTO.getLogradouro());
            enderecoToUpdate.setNumero(enderecoDTO.getNumero());
            enderecoToUpdate.setUf(enderecoDTO.getUf());
            return enderecoMapper.toDto(enderecoRepository.save(enderecoToUpdate));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar endereço: " + e.getMessage());
        }
    }



    public void delete(Integer id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");

        if (!enderecoRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");

        try {
            enderecoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar endereço: " + e.getMessage());
        }
    }

    public EnderecoDTO findById(Integer id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");

        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado"));

        try {
            return enderecoMapper.toDto(endereco);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço: " + e.getMessage());
        }
    }

    public List<EnderecoDTO> findAll() {
        if (enderecoRepository.findAll().isEmpty()) {
            log.error("Nenhum endereço encontrado");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum endereço encontrado");
        }

        try {
            List<Endereco> enderecos = enderecoRepository.findAll();
            return enderecoMapper.toDto(enderecos);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereços: " + e.getMessage());
        }
    }
}
