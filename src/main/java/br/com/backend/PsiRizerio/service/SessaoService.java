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
import org.springframework.stereotype.Service;

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

    public SessaoDTO save(SessaoDTO schedule) {
        try {
            log.info("Tentando salvar: {}", schedule);
            Sessao sessaoEntity = scheduleMapper.toEntity(schedule);

            sessaoRepository.save(sessaoEntity);
            return scheduleMapper.toDto(sessaoEntity);
        } catch (Exception e) {
            log.error("Erro ao salvar consulta: {}", e.getMessage(), e);
            throw new SaveSessaoException("Erro ao salvar consulta", e);
        }
    }

    public SessaoDTO update(Integer id, SessaoDTO sessaoDTO) {
            Sessao sessaoToUpdate = sessaoRepository.findById(id).orElseThrow(() -> new FindSessaoException("Consulta não encontrada"));
            sessaoRepository.save(sessaoToUpdate);
            return scheduleMapper.toDto(sessaoToUpdate);
    }

    public List<Sessao> findAll() {
       List<Sessao> sessaos = sessaoRepository.findAll();
       return sessaos;
    }

    public SessaoDTO findById(Integer id) {
        Sessao sessao = sessaoRepository.findById(id).orElseThrow(() -> new FindSessaoException("Consulta não encontrada"));
        return scheduleMapper.toDto(sessao);
    }

    public void cancelSchedule(Integer id) {
        var schedule = sessaoRepository.findById(id).orElseThrow(() -> new FindSessaoException("Consulta não encontrada"));
        sessaoRepository.delete(schedule);
    }
}
