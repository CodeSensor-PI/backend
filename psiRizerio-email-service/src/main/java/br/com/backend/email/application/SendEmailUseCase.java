package br.com.backend.email.application;

import br.com.backend.email.domain.Email;

public interface SendEmailUseCase {
    void execute(Email email);
}
