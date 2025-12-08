package br.com.backend.email.infrastructure.email;

import br.com.backend.email.domain.Email;

public interface EmailSender {
    void send(Email email);
}
