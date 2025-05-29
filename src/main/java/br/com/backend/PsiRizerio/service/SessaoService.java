package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoDiaResponseDTO;
import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoGraficoDadosDTO;
import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoKpiQtdCanceladaDTO;
import br.com.backend.PsiRizerio.dto.sessaoDTO.SessaoKpiResponseDTO;
import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.repositories.SessaoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
// Removed unused imports for PageRequest and Pageable
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    private final SessaoMapper sessaoMapper;
    private final PacienteRepository pacienteRepository;

    public Sessao createSessao(Sessao sessao) {
        if (sessaoRepository.existsByDataAndHora(sessao.getData(), sessao.getHora()))
            throw new EntidadeConflitoException();

        if (sessaoRepository.existsByDataAndHoraBetween(sessao.getData(), sessao.getHora(), sessao.getHora().plusHours(1)))
            throw new EntidadeConflitoException();

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

        if (sessaoRepository.existsByDataAndHoraBetweenAndIdNot(sessao.getData(), sessao.getHora(), sessao.getHora().plusHours(1).minusSeconds(1), id))
            throw new EntidadeConflitoException();


        sessaoToUpdate.setData(sessao.getData());
        sessaoToUpdate.setHora(sessao.getHora());
        sessaoToUpdate.setStatusSessao(sessao.getStatusSessao());
        sessaoToUpdate.setAnotacao(sessao.getAnotacao());
        sessaoToUpdate.setUpdatedAt(LocalDateTime.now());
        sessaoToUpdate.setFkPaciente(sessao.getFkPaciente());
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

    public List<SessaoKpiResponseDTO> getKpiSessoesSemanaAtualEAnterior() {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int semanaAtual = now.get(weekFields.weekOfWeekBasedYear());
        int semanaAnterior = semanaAtual == 1 ? 52 : semanaAtual - 1;
        int anoAtual = now.getYear();
        int anoAnterior = semanaAtual == 1 ? anoAtual - 1 : anoAtual;

        return sessaoRepository.findKpiSessoesSemanaAtualEAnterior(
                anoAtual, semanaAtual, anoAnterior, semanaAnterior
        );
    }

    public List<SessaoDiaResponseDTO> getSessoesDoDia() {
        return sessaoRepository.findSessoesDoDia();
    }

    public SessaoKpiQtdCanceladaDTO getKpiQtdCanceladas() {

        Double qtdCancelada = sessaoRepository.getPercentualCanceladasSemana(StatusSessao.CANCELADA.name());

        if (qtdCancelada == null) {
            qtdCancelada = 0.0;
        }

        return new SessaoKpiQtdCanceladaDTO(qtdCancelada);
    }

    public List<SessaoGraficoDadosDTO> getDadosGrafico() {

        List<Object[]> resultados = sessaoRepository.getDadosGrafico(LocalDate.now().getYear(), StatusSessao.CANCELADA.name(), StatusSessao.CONCLUIDA.name());

        List<SessaoGraficoDadosDTO> dtos = resultados.stream().map(obj -> {
            Long qtdCancelada = ((Number) obj[0]).longValue();
            Long qtdConcluida = ((Number) obj[1]).longValue();
            Integer mesInt = ((Number) obj[2]).intValue();

            // opcional: converter número do mês para nome do mês em pt-BR
            String mesNome = Month.of(mesInt)
                    .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

            return new SessaoGraficoDadosDTO(qtdCancelada, qtdConcluida, mesNome);
        }).toList();

        return dtos;
    }

}
