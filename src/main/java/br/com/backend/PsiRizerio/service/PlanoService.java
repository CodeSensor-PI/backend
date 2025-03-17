package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.dto.PlanoDTO;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.mapper.PlanoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PlanoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlanoService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final PlanoRepository planoRepository;
    private final PlanoMapper planoMapper;

    @Autowired
    public PlanoService(PlanoRepository planoRepository, PlanoMapper planoMapper) {
        this.planoRepository = planoRepository;
        this.planoMapper = planoMapper;
    }

    public PlanoDTO createPlano(PlanoDTO planoDTO) {
        if (planoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plano inválido");
        }

        try {
            return planoMapper.toDto(planoRepository.save(planoMapper.toEntity(planoDTO)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar plano: " + e.getMessage());
        }
    }

    public PlanoDTO update(Integer id, PlanoDTO planoDTO) {
        if (planoDTO == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plano inválido");

        try {
            var planoToUpdate = planoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
            planoToUpdate.setCategoria(planoDTO.getCategoria());
            planoToUpdate.setPreco(planoDTO.getPreco());
            return planoMapper.toDto(planoRepository.save(planoToUpdate));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar plano: " + e.getMessage());
        }
    }

    public List<PlanoDTO> findAll() {
        if (planoRepository.findAll().isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum plano encontrado");

        if (planoRepository.findAll() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum plano encontrado");

        try {
            return planoMapper.toDto(planoRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar planos: " + e.getMessage());
        }
    }

    public PlanoDTO findById(Integer id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");

        try {
            Plano plano = planoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
            return planoMapper.toDto(plano);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar plano: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");

        if (!planoRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado");

        try {
            planoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar plano: " + e.getMessage());
        }
    }


}
