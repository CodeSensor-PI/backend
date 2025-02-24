package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.persistence.entities.Schedule;
import br.com.backend.PsiRizerio.persistence.repositories.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule save(Schedule schedule) {
        try {
            return scheduleRepository.save(schedule);
        } catch (Exception e) {
            log.error("Erro ao salvar consulta: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar consulta");
        }
    }

    public Schedule update(Long id,Schedule schedule) {
        try {
            Schedule scheduleToUpdate = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
            scheduleToUpdate.setStart_time(schedule.getStart_time());
            scheduleToUpdate.setEnd_time(schedule.getEnd_time());
            scheduleToUpdate.setTitle(schedule.getTitle());
            scheduleToUpdate.setDescription(schedule.getDescription());
            scheduleToUpdate.setData(schedule.getData());
            scheduleRepository.save(scheduleToUpdate);
            return scheduleToUpdate;
        }catch (Exception e) {
            log.error("Erro ao atualizar consulta: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar consulta");
        }
    }

    public List<Schedule> findAll() {
        try {
            return scheduleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar consultas");
        }
    }

    public Schedule findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        return schedule;
    }

    public void cancelSchedule(Long id) {
        try {
            if (!scheduleRepository.existsById(id)) {
                throw new RuntimeException("Consulta não encontrada");
            }
            scheduleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cancelar consulta");
        }
    }
}
