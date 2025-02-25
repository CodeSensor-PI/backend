package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.schedule.FindScheduleException;
import br.com.backend.PsiRizerio.exception.schedule.SaveScheduleException;
import br.com.backend.PsiRizerio.exception.schedule.ScheduleConflictException;
import br.com.backend.PsiRizerio.mapper.ScheduleMapper;
import br.com.backend.PsiRizerio.model.ScheduleDTO;
import br.com.backend.PsiRizerio.persistence.entities.Schedule;
import br.com.backend.PsiRizerio.persistence.repositories.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public ScheduleDTO save(ScheduleDTO schedule) {
        try {
            log.info("Tentando salvar: {}", schedule);
            Schedule scheduleEntity = scheduleMapper.toEntity(schedule);

            if (scheduleEntity.getStart_time().isAfter(scheduleEntity.getEnd_time())) {
                throw new ScheduleConflictException("Horário inválido");
            }

            scheduleRepository.save(scheduleEntity);
            return scheduleMapper.toDto(scheduleEntity);
        } catch (Exception e) {
            log.error("Erro ao salvar consulta: {}", e.getMessage(), e);
            throw new SaveScheduleException("Erro ao salvar consulta", e);
        }
    }

    public ScheduleDTO update(Long id,ScheduleDTO scheduleDTO) {
            Schedule scheduleToUpdate = scheduleRepository.findById(id).orElseThrow(() -> new FindScheduleException("Consulta não encontrada"));
            scheduleToUpdate.setStart_time(scheduleDTO.getStart_time());
            scheduleToUpdate.setEnd_time(scheduleDTO.getEnd_time());
            scheduleToUpdate.setTitle(scheduleDTO.getTitle());
            scheduleToUpdate.setDescription(scheduleDTO.getDescription());
            scheduleToUpdate.setData(scheduleDTO.getData());
            scheduleRepository.save(scheduleToUpdate);
            return scheduleMapper.toDto(scheduleToUpdate);
    }

    public List<Schedule> findAll() {
       List<Schedule> schedules = scheduleRepository.findAll();
       return schedules;
    }

    public ScheduleDTO findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new FindScheduleException("Consulta não encontrada"));
        return scheduleMapper.toDto(schedule);
    }

    public void cancelSchedule(Long id) {
        var schedule = scheduleRepository.findById(id).orElseThrow(() -> new FindScheduleException("Consulta não encontrada"));
        scheduleRepository.delete(schedule);
    }
}
