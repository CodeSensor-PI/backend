package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import br.com.backend.PsiRizerio.persistence.repositories.SessaoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    private final SessaoMapper sessaoMapper;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public SessaoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper, UsuarioRepository usuarioRepository) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public Sessao createSessao(Sessao sessao) {
        if (sessaoRepository.existsByDtHrSessao(sessao.getDtHrSessao())) throw new EntidadeConflitoException();

        if (sessaoRepository.existsByDtHrSessaoBetween(sessao.getDtHrSessao(), sessao.getDtHrSessao().plusHours(1))) throw new EntidadeConflitoException();

        Integer clienteId = sessao.getFkCliente().getId();
        Usuario usuario = usuarioRepository.findById(clienteId)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        sessao.setFkCliente(usuario);

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
        if (sessaoRepository.findAll().isEmpty()) throw new EntidadeNaoEncontradaException();

        return sessaoRepository.findAll();
    }

    public Sessao findById(Integer id) {
        return sessaoRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));
    }

    public void delete(Integer id) {
        if (!sessaoRepository.existsById(id)) throw new EntidadeNaoEncontradaException();

        sessaoRepository.deleteById(id);
    }

    public List<Sessao> findByDtHrSessaoBetween(LocalDateTime start, LocalDateTime end) {
        return sessaoRepository.findByDtHrSessaoBetween(start, end);
    }

}
