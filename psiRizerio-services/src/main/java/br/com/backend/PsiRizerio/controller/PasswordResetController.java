package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.PasswordResetToken;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import br.com.backend.PsiRizerio.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PacienteRepository pacienteRepository;
    private final PasswordResetService passwordResetService;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange:email-exchange}")
    private String emailExchange;
    @Value("${app.rabbitmq.routing-key:email.send}")
    private String emailRoutingKey;

    @PostMapping("/request")
    public ResponseEntity<?> requestReset(@RequestParam String email) {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElse(null);
        if (paciente == null) {
            return ResponseEntity.badRequest().body("Paciente não encontrado");
        }
        PasswordResetToken token = passwordResetService.createToken(paciente);
        String resetLink = "https://seusite.com/resetar-senha?token=" + token.getToken();
        String subject = "Redefinição de senha";
        String body = String.format("Olá, %s!\n\nPara redefinir sua senha, acesse o link abaixo:\n%s\n\nEste link expira em 1 hora.", paciente.getNome(), resetLink);
        Map<String, Object> payload = Map.of(
                "to", paciente.getEmail(),
                "subject", subject,
                "body", body
        );
        rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, payload);
        return ResponseEntity.ok("E-mail de redefinição enviado");
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmReset(@RequestParam String token, @RequestParam String novaSenha) {
        var resetTokenOpt = passwordResetService.validateToken(token);
        if (resetTokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Token inválido ou expirado");
        }
        PasswordResetToken resetToken = resetTokenOpt.get();
        Paciente paciente = resetToken.getPaciente();
        paciente.setSenha(passwordEncoder.encode(novaSenha));
        pacienteRepository.save(paciente);
        passwordResetService.consumeToken(resetToken);
        return ResponseEntity.ok("Senha redefinida com sucesso");
    }
}

