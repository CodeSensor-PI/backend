package br.com.backend.PsiRizerio.exception;

public class EntidadeSemConteudoException extends RuntimeException {
    public EntidadeSemConteudoException() {
        super("Entidade sem conteúdo");
    }
}
