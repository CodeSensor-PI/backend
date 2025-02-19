package br.com.backend.PsiRizerio.exception.schedule;

public class ScheduleConflictException extends RuntimeException {
    public ScheduleConflictException(String message) {
        super(message);
    }
}
