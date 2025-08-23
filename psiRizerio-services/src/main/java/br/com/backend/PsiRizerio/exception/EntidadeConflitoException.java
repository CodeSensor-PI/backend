package br.com.backend.PsiRizerio.exception;

public class EntidadeConflitoException extends RuntimeException{
    public EntidadeConflitoException(String message) {
        super(message);
    }

    public EntidadeConflitoException() {
    }
}
