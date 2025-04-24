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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        if (sessaoRepository.existsByDataAndHora(sessao.getData(), sessao.getHora())) throw new EntidadeConflitoException();

        if (sessaoRepository.existsByDataAndHoraBetween(sessao.getData(), sessao.getHora(), sessao.getHora().plusHours(1))) throw new EntidadeConflitoException();

        Integer clienteId = sessao.getFkCliente().getId();
        Usuario usuario = usuarioRepository.findById(clienteId)
                .orElseThrow((EntidadeNaoEncontradaException::new));
        sessao.setFkCliente(usuario);
        sessao.setCreatedAt(LocalDateTime.now());

        return sessaoRepository.save(sessao);
    }

    public Sessao update(Integer id, Sessao sessao) {
        Sessao sessaoToUpdate = sessaoRepository.findById(id)
                .orElseThrow((EntidadeConflitoException::new));

        if (sessaoRepository.existsByDataAndHoraBetweenAndIdNot(sessao.getData(), sessao.getHora(), sessao.getHora().plusHours(1), id)) throw new EntidadeConflitoException();

        sessaoToUpdate.setUpdatedAt(LocalDateTime.now());
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

    public List<Sessao> findByDtHrSessaoBetween(LocalDate data, LocalTime hora, LocalTime hora2) {
        return sessaoRepository.findByDataAndHoraBetween(data, hora, hora2);
    }

    public List<Sessao> findByUsuarioId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow((EntidadeNaoEncontradaException::new));
        return sessaoRepository.findByFkCliente(usuario);
    }

}
