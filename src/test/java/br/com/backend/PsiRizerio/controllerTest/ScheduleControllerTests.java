package br.com.backend.PsiRizerio.controllerTest;

import br.com.backend.PsiRizerio.persistence.entities.Schedule;
import br.com.backend.PsiRizerio.persistence.repositories.ScheduleRepository;
import br.com.backend.PsiRizerio.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
public class ScheduleControllerTests {

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void createScheduleTest() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setStart_time(LocalTime.now());
        schedule.setEnd_time(LocalTime.now());
        schedule.setTitle("Teste");
        schedule.setDescription("Teste");
        schedule.setData(LocalDate.now());

        Mockito.when(scheduleRepository.save(Mockito.any(Schedule.class))).thenReturn(new Schedule());

        scheduleService.save(schedule);
    }

    @Test
    void updateScheduleTest() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setStart_time(LocalTime.now());
        schedule.setEnd_time(LocalTime.now());
        schedule.setTitle("Teste");
        schedule.setDescription("Teste");
        schedule.setData(LocalDate.now());

        Mockito.when(scheduleRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(new Schedule()));
    }

    @Test
    void findByIdTest() throws Exception {
        Mockito.when(scheduleRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(new Schedule()));
    }

    @Test
    void deleteTest() throws Exception {
        Mockito.when(scheduleRepository.existsById(Mockito.anyLong())).thenReturn(true);
    }

}
