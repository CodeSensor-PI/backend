package br.com.backend.PsiRizerio.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class MuitasRequisicoesException extends ResponseStatusException {

  private final Long retryAfter;

  public MuitasRequisicoesException(String message, Long retryAfter) {
    super(HttpStatus.TOO_MANY_REQUESTS, message);
    this.retryAfter = retryAfter;
  }
}
