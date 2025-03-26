package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.planoDTO.PlanoCreateDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoResponseDTO;
import br.com.backend.PsiRizerio.dto.planoDTO.PlanoUpdateDTO;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.PlanoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
import br.com.backend.PsiRizerio.persistence.repositories.PlanoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PlanoCreateDTO createPlano(PlanoCreateDTO planoCreateDTO) {
        if (planoRepository.existsByCategoria(planoCreateDTO.getCategoria())) throw new EntidadeConflitoException();

        Plano plano = planoMapper.toEntity(planoCreateDTO);
        Plano planoToSave = planoRepository.save(plano);
        return planoMapper.toDto(planoToSave);
    }

    public PlanoUpdateDTO update(Integer id, PlanoUpdateDTO planoUpdateDTO) {
        Plano planoToUpdate = planoRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        if (planoRepository.existsByCategoriaAndIdNot(planoUpdateDTO.getCategoria(), id)) throw new EntidadeConflitoException();

        planoToUpdate.setCategoria(planoUpdateDTO.getCategoria());
        planoToUpdate.setPreco(planoUpdateDTO.getPreco());
        return planoMapper.toDtoUpdate(planoRepository.save(planoToUpdate));
    }

    public List<PlanoResponseDTO> findAll() {
        if (planoRepository.findAll().isEmpty()) throw new EntidadeSemConteudoException();

        return planoMapper.toDtoList(planoRepository.findAll());
    }

    public PlanoResponseDTO findById(Integer id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));
        return planoMapper.toDtoResponse(plano);
    }

    public void delete(Integer id) {
        if (!planoRepository.existsById(id)) throw new EntidadeNaoEncontradaException();

        planoRepository.deleteById(id);
    }
}
