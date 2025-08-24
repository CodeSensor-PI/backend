package br.com.backend.email.infrastructure.email;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    @ConditionalOnMissingBean(JavaMailSender.class)
    public JavaMailSender javaMailSender(Environment env) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        String host = env.getProperty("spring.mail.host");
        Integer port = env.getProperty("spring.mail.port", Integer.class);
        String username = env.getProperty("spring.mail.username");
        String password = env.getProperty("spring.mail.password");
        String protocol = env.getProperty("spring.mail.protocol", "smtp");

        if (host != null && !host.isBlank()) sender.setHost(host);
        if (port != null && port > 0) sender.setPort(port);
        if (username != null && !username.isBlank()) sender.setUsername(username);
        if (password != null && !password.isBlank()) sender.setPassword(password);

        sender.setDefaultEncoding(env.getProperty("spring.mail.default-encoding", "UTF-8"));

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth", "true"));
        props.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable", "true"));
        props.put("mail.smtp.connectiontimeout", env.getProperty("spring.mail.properties.mail.smtp.connectiontimeout", "5000"));
        props.put("mail.smtp.timeout", env.getProperty("spring.mail.properties.mail.smtp.timeout", "5000"));
        props.put("mail.smtp.writetimeout", env.getProperty("spring.mail.properties.mail.smtp.writetimeout", "5000"));
        return sender;
    }
}

