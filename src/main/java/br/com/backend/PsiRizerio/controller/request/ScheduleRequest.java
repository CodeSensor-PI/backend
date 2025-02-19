package br.com.backend.PsiRizerio.controller.request;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public record ScheduleRequest(
    String title,
    String description,
    LocalDateTime data,
    Time start_time,
    Time end_time,
    Long userId
) {
}
