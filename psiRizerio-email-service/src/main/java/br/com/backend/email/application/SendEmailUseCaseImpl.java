package br.com.backend.email.application;

import br.com.backend.email.domain.Email;
import br.com.backend.email.infrastructure.email.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailUseCaseImpl implements  SendEmailUseCase {

    private final EmailSender emailSender;

    public SendEmailUseCaseImpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void execute(Email email) {
        emailSender.send(email);
    }
}
