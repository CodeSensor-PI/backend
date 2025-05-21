package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.enums.TipoSessao;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.repositories.SessaoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    private final SessaoMapper sessaoMapper;
    private final PacienteRepository pacienteRepository;

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
        sessaoToUpdate.setStatusSessao(sessao.getStatusSessao());
        sessaoToUpdate.setAnotacao(sessao.getAnotacao());
        sessaoToUpdate.setUpdatedAt(LocalDateTime.now());
        return sessaoRepository.save(sessaoToUpdate);
    }

    public Sessao cancelarSessao(Integer id, Sessao sessao) {
        Sessao sessaoToUpdate = sessaoRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        sessaoToUpdate.setStatusSessao(StatusSessao.CANCELADA);
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

    public List<Sessao> findByStatusPendente(StatusSessao statusSessao) {
        if (statusSessao == null) throw new EntidadeNaoEncontradaException();

        if (sessaoRepository.findByStatusSessao(statusSessao).isEmpty()) throw new EntidadeNaoEncontradaException();

        return sessaoRepository.findByStatusSessao(statusSessao);
    }


    public List<Sessao> findByDataHoraDisponivel(LocalDate data, LocalTime hora, LocalTime hora2) {
        if (sessaoRepository.existsByDataAndHoraBetween(data, hora, hora2)) throw new EntidadeConflitoException();

        return sessaoRepository.findByDataAndHoraBetween(data, hora, hora2);
    }

    public List<LocalTime> findHorariosDisponiveis(LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        List<Sessao> sessoesOcupadas = sessaoRepository.findByDataAndHoraBetween(data, horaInicio, horaFim);
        List<LocalTime> horariosOcupados = sessoesOcupadas.stream()
                .map(Sessao::getHora)
                .toList();

        List<LocalTime> horariosDisponiveis = new ArrayList<>();
        for (LocalTime hora = horaInicio; hora.isBefore(horaFim); hora = hora.plusMinutes(60)) {
            if (!horariosOcupados.contains(hora)) {
                horariosDisponiveis.add(hora);
            }
        }
        return horariosDisponiveis;
    }

    public List<LocalDate> findDataPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<LocalDate> datas = new ArrayList<>();
        for (LocalDate data = dataInicio; !data.isAfter(dataFim); data = data.plusDays(1)) {
            datas.add(data);
        }
        return datas;
    }



}
