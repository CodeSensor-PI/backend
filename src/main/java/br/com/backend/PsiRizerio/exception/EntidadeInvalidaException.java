package br.com.backend.PsiRizerio.exception;

public class EntidadeInvalidaException extends RuntimeException{
    public EntidadeInvalidaException(String message) {
        super(message);
    }

    public EntidadeInvalidaException() {
    }
}
