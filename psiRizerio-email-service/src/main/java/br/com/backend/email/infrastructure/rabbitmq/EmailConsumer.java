package br.com.backend.email.infrastructure.rabbitmq;

import br.com.backend.email.application.SendEmailUseCase;
import br.com.backend.email.domain.Email;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EmailConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailConsumer.class);

    private final SendEmailUseCase sendEmailUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public EmailConsumer(SendEmailUseCase sendEmailUseCase) {
        this.sendEmailUseCase = sendEmailUseCase;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue:emailQueue}")
    public void consume(Message message) {
        String body = new String(message.getBody(), StandardCharsets.UTF_8).trim();
        EmailDTO dto;
        try {
            dto = objectMapper.readValue(body, EmailDTO.class);
        } catch (Exception e) {
            log.error("Falha ao converter payload em JSON. contentType={}, routingKey={}, payload='{}'",
                    message.getMessageProperties().getContentType(),
                    message.getMessageProperties().getReceivedRoutingKey(),
                    body, e);
            return;
        }

        try {
            Email email = new Email(dto.getTo(), dto.getSubject(), dto.getBody());
            sendEmailUseCase.execute(email);
            log.info("Email enviado para {} com assunto '{}'", dto.getTo(), dto.getSubject());
        } catch (Exception e) {
            log.error("Falha ao enviar e-mail para {} com assunto '{}'",
                    dto.getTo(), dto.getSubject(), e);
        }
    }

    public static class EmailDTO {
        private String to;
        private String subject;
        private String body;
        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
    }

}
