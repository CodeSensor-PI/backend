package br.com.backend.PsiRizerio.exception.schedule;

public class SaveSessaoException extends RuntimeException {
    public SaveSessaoException(String message, Exception e) {
        super(message);
    }
}
