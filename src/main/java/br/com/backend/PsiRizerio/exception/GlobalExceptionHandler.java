package br.com.backend.PsiRizerio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadeConflitoException.class)
    public ResponseEntity<String> handleRecursoNaoEncontradoException(EntidadeConflitoException ec) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ec.getMessage());
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> handleRecursoNaoEncontradoException(EntidadeNaoEncontradaException ene) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ene.getMessage());
    }

    @ExceptionHandler(EntidadeInvalidaException.class)
    public ResponseEntity<String> handleRecursoNaoEncontradoException(EntidadeInvalidaException eie) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eie.getMessage());
    }

    @ExceptionHandler(EntidadeSemConteudoException.class)
    public ResponseEntity<String> handleRecursoNaoEncontradoException(EntidadeSemConteudoException esc) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(esc.getMessage());
    }
}
