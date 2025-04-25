package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.repositories.SessaoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    private final SessaoMapper sessaoMapper;
    private final PacienteRepository pacienteRepository;

    @Autowired
    public SessaoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper, PacienteRepository pacienteRepository) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
        this.pacienteRepository = pacienteRepository;
    }

    public Sessao createSessao(Sessao sessao) {
        if (sessaoRepository.existsByDtHrSessao(sessao.getDtHrSessao())) throw new EntidadeConflitoException();

        if (sessaoRepository.existsByDtHrSessaoBetween(sessao.getDtHrSessao(), sessao.getDtHrSessao().plusHours(1))) throw new EntidadeConflitoException();

        Integer pacienteId = sessao.getFkPaciente().getId();
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow((EntidadeNaoEncontradaException::new));
        sessao.setFkPaciente(paciente);
        sessao.setCreatedAt(LocalDateTime.now());

        return sessaoRepository.save(sessao);
    }

    public Sessao update(Integer id, Sessao sessao) {
        Sessao sessaoToUpdate = sessaoRepository.findById(id)
                .orElseThrow((EntidadeConflitoException::new));

        if (sessaoRepository.existsByDtHrSessaoBetweenAndIdNot(sessao.getDtHrSessao(), sessao.getDtHrSessao().plusHours(1), id)) throw new EntidadeConflitoException();

        sessaoToUpdate.setDtHrSessao(sessao.getDtHrSessao());
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

    public List<Sessao> findByDtHrSessaoBetween(LocalDateTime start, LocalDateTime end) {
        return sessaoRepository.findByDtHrSessaoBetween(start, end);
    }

    public List<Sessao> findByPacienteId(Integer pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow((EntidadeNaoEncontradaException::new));
        return sessaoRepository.findByFkPaciente(paciente);
    }

}
