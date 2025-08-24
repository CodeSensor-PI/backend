package br.com.backend.email.infrastructure.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SmtpStartupLogger implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SmtpStartupLogger.class);
    private final Environment env;

    public SmtpStartupLogger(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) {
        String host = env.getProperty("spring.mail.host", "");
        String port = env.getProperty("spring.mail.port", "");
        String username = env.getProperty("spring.mail.username", "");
        String password = env.getProperty("spring.mail.password");
        boolean hasPassword = password != null && !password.isBlank();
        String from = env.getProperty("app.mail.from", "");
        String starttls = env.getProperty("spring.mail.properties.mail.smtp.starttls.enable", "");
        String sslEnable = env.getProperty("spring.mail.properties.mail.smtp.ssl.enable", "");
        log.info("SMTP config: host={}, port={}, username={}, from={}, starttls={}, ssl.enable={}, passwordPresent={}",
                host, port, username, from, starttls, sslEnable, hasPassword);
    }
}

