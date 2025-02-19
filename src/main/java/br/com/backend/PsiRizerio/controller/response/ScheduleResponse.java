package br.com.backend.PsiRizerio.controller.response;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public record ScheduleResponse(
    Long id,
    String title,
    String description,
    LocalDateTime date,
    Time startTime,
    Time endTime,
    Long userId
) {
}
