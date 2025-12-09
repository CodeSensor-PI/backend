package br.com.backend.email.infrastructure.email;

import br.com.backend.email.domain.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Classe desativada: não é mais um @Component
// Mantida apenas como referência, mas não é registrada como bean.
public class NoOpEmailSender implements EmailSender {
    private static final Logger log = LoggerFactory.getLogger(NoOpEmailSender.class);

    @Override
    public void send(Email email) {
        log.info("[NO-OP - DESATIVADO] Este sender não deve ser usado em produção.");
    }
}
