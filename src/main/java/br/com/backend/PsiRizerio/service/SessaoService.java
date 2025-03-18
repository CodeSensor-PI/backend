package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.schedule.FindSessaoException;
import br.com.backend.PsiRizerio.exception.schedule.SaveSessaoException;
import br.com.backend.PsiRizerio.exception.schedule.ConflictSessaoException;
import br.com.backend.PsiRizerio.mapper.SessaoMapper;
import br.com.backend.PsiRizerio.dto.SessaoDTO;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.repositories.SessaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final SessaoRepository sessaoRepository;
    private final SessaoMapper scheduleMapper;

    public SessaoService(SessaoRepository sessaoRepository, SessaoMapper scheduleMapper) {
        this.sessaoRepository = sessaoRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public SessaoDTO save(SessaoDTO sessaoDTO) {
        if (sessaoDTO.getDtHrSessao() == null || sessaoDTO.getStatusSessao() == null || sessaoDTO.getAnotacao() == null
                || sessaoDTO.getTipo() == null || sessaoDTO.getFkCliente() == null) {
            log.error("Campos inválidos");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos inválida");
        }

        if (sessaoDTO == null) {
            log.error("Consulta inválida");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consulta inválida");
        }

        if (sessaoRepository.existsByDtHrSessao(sessaoDTO.getDtHrSessao())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Consulta já cadastrada");
        }

        LocalDateTime start = sessaoDTO.getDtHrSessao().minusHours(1);
        LocalDateTime end = sessaoDTO.getDtHrSessao().plusHours(1);

        if (sessaoRepository.existsByDtHrSessaoBetween(start, end)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma consulta agendada dentro de 1 hora deste horário");
        }

        try {
            sessaoDTO.setCreatedAt(LocalDateTime.now());
            sessaoDTO.setUpdatedAt(LocalDateTime.now());
            Sessao sessaoEntity = scheduleMapper.toEntity(sessaoDTO);
            sessaoRepository.save(sessaoEntity);
            log.info("Tentando salvar: {}", sessaoDTO);
            return scheduleMapper.toDto(sessaoEntity);
        } catch (Exception e) {
            log.error("Erro ao salvar consulta: {}", e.getMessage(), e);
            throw new SaveSessaoException("Erro ao salvar consulta", e);
        }
    }

    public SessaoDTO update(Integer id, SessaoDTO sessaoDTO) {
            Sessao sessaoToUpdate = sessaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada"));


            if (sessaoRepository.existsByDtHrSessaoBetweenAndIdNot(sessaoDTO.getDtHrSessao().minusHours(1), sessaoDTO.getDtHrSessao().plusHours(1), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma consulta agendada dentro de 1 hora deste horário");
            }

            try {
                sessaoToUpdate.setDtHrSessao(sessaoDTO.getDtHrSessao());
                sessaoToUpdate.setStatusSessao(sessaoDTO.getStatusSessao());
                sessaoToUpdate.setAnotacao(sessaoDTO.getAnotacao());
                sessaoToUpdate.setTipo(sessaoDTO.getTipo());
                sessaoToUpdate.setFkCliente(sessaoDTO.getFkCliente());
                sessaoToUpdate.setUpdatedAt(LocalDateTime.now());
                sessaoRepository.save(sessaoToUpdate);
                return scheduleMapper.toDto(sessaoToUpdate);
            }catch (Exception e) {
                log.error("Erro ao atualizar consulta: {}", e.getMessage(), e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar consulta");
            }


    }

    public List<SessaoDTO> findAll() {
        if (sessaoRepository.findAll().isEmpty()) {
            log.error("Nenhuma consulta encontrada");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma consulta encontrada");
        }

        try {
            List<Sessao> schedules = sessaoRepository.findAll();
            return scheduleMapper.toDto(schedules);
        } catch (Exception e) {
            log.error("Erro ao buscar consultas: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar consultas");
        }

    }

    public SessaoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");
        }
        Sessao sessao = sessaoRepository.findById(id).orElseThrow(() -> new FindSessaoException("Consulta não encontrada"));

        try {
            return scheduleMapper.toDto(sessao);
        }catch (Exception e) {
            log.error("Erro ao buscar consulta: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar consulta");
        }

    }

    public void cancelSchedule(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        if (!sessaoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada");
        }

        try {
            sessaoRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Erro ao deletar consulta: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar consulta");
        }
    }
}
