package br.com.backend.PsiRizerio.mapper;

import br.com.backend.PsiRizerio.model.ScheduleDTO;
import br.com.backend.PsiRizerio.persistence.entities.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class ScheduleMapper {

    @Named(value="toEntity")
    public Schedule toEntity(ScheduleDTO model) {
        Schedule schedule = new Schedule();
        schedule.setId(model.getId());
        schedule.setData(model.getData());
        schedule.setTitle(model.getTitle());
        schedule.setDescription(model.getDescription());
        schedule.setStart_time(model.getStart_time());
        schedule.setEnd_time(model.getEnd_time());
        schedule.setUserId(model.getUserId());
        return schedule;
    }

    @Named(value="toDto")
    public ScheduleDTO toDto(Schedule entity) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(entity.getId());
        scheduleDTO.setData(entity.getData());
        scheduleDTO.setTitle(entity.getTitle());
        scheduleDTO.setDescription(entity.getDescription());
        scheduleDTO.setStart_time(entity.getStart_time());
        scheduleDTO.setEnd_time(entity.getEnd_time());
        scheduleDTO.setUserId(entity.getUserId());
        return scheduleDTO;
    }
}
