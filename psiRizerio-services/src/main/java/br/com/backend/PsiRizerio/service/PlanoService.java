package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.PlanoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Plano;
import br.com.backend.PsiRizerio.persistence.repositories.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoService {
    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PlanoRepository planoRepository;
    private final PlanoMapper planoMapper;

    public Plano createPlano(Plano plano) {
        if (planoRepository.existsByCategoria(plano.getCategoria())) throw new EntidadeConflitoException();

        return planoRepository.save(plano);
    }

    public Plano update(Integer id, Plano plano) {
        Plano planoToUpdate = planoRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        if (planoRepository.existsByCategoriaAndIdNot(plano.getCategoria(), id)) throw new EntidadeConflitoException();

        planoToUpdate.setCategoria(plano.getCategoria());
        planoToUpdate.setPreco(plano.getPreco());
        return planoRepository.save(planoToUpdate);
    }

    public List<Plano> findAll() {
        if (planoRepository.findAll().isEmpty()) throw new EntidadeSemConteudoException();

        return planoRepository.findAll();
    }

    public Plano findById(Integer id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));
        return plano;
    }

    public void delete(Integer id) {
        if (!planoRepository.existsById(id)) throw new EntidadeNaoEncontradaException();

        planoRepository.deleteById(id);
    }
}
