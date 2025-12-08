package br.com.backend.PsiRizerio.exception;

public class EntidadeNaoAutorizadaException extends RuntimeException {
    public EntidadeNaoAutorizadaException(String message) {
        super(message);
    }

    public EntidadeNaoAutorizadaException() {
    }
}
