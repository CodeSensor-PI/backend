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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        if (sessaoRepository.existsByDataAndHora(sessao.getData(), sessao.getHora())) throw new EntidadeConflitoException();

        if (sessaoRepository.existsByDataAndHoraBetween(sessao.getData(), sessao.getHora(), sessao.getHora().plusHours(1))) throw new EntidadeConflitoException();

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

        if (sessaoRepository.existsByDataAndHoraBetweenAndIdNot(sessao.getData(), sessao.getHora(), sessao.getHora().plusHours(1), id)) throw new EntidadeConflitoException();

        sessaoToUpdate.setData(sessao.getData());
        sessaoToUpdate.setHora(sessao.getHora());
        sessaoToUpdate.setAnotacao(sessao.getAnotacao());
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

    public List<Sessao> findByPacienteId(Integer pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow((EntidadeNaoEncontradaException::new));
        return sessaoRepository.findByFkPaciente(paciente);
    }

}
