package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.repositories.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    private final SessaoMapper sessaoMapper;

    @Autowired
    public SessaoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
    }

    public Sessao createSessao(Sessao sessao) {
        if (sessaoRepository.existsByDtHrSessao(sessao.getDtHrSessao())) throw new EntidadeConflitoException();

        if (sessaoRepository.existsByDtHrSessaoBetween(sessao.getDtHrSessao(), sessao.getDtHrSessao().plusHours(1))) throw new EntidadeConflitoException();

        return sessaoRepository.save(sessao);
    }

    public Sessao update(Integer id, Sessao sessao) {
        Sessao sessaoToUpdate = sessaoRepository.findById(id)
                .orElseThrow((EntidadeConflitoException::new));

        if (sessaoRepository.existsByDtHrSessaoBetweenAndIdNot(sessao.getDtHrSessao(), sessao.getDtHrSessao().plusHours(1), id)) throw new EntidadeConflitoException();

        sessaoToUpdate.setDtHrSessao(sessao.getDtHrSessao());
        return sessaoRepository.save(sessaoToUpdate);
    }

    public List<Sessao> findAll() {
        if (sessaoRepository.findAll().isEmpty()) throw new EntidadeConflitoException();

        return sessaoRepository.findAll();
    }

    public Sessao findById(Integer id) {
        return sessaoRepository.findById(id)
                .orElseThrow((EntidadeConflitoException::new));
    }

    public void delete(Integer id) {
        if (!sessaoRepository.existsById(id)) throw new EntidadeConflitoException();

        sessaoRepository.deleteById(id);
    }

    public List<Sessao> findByDtHrSessaoBetween(LocalDateTime start, LocalDateTime end) {
        return sessaoRepository.findByDtHrSessaoBetween(start, end);
    }

}
