package br.com.backend.PsiRizerio.exception.schedule;

public class SaveScheduleException extends RuntimeException {
    public SaveScheduleException(String message, Exception e) {
        super(message);
    }
}
