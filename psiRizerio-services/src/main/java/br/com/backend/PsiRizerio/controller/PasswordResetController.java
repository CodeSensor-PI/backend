package br.com.backend.PsiRizerio.controller;

import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import br.com.backend.PsiRizerio.persistence.entities.PasswordResetToken;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PsicologoRepository;
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
    private final PsicologoRepository psicologoRepository;
    private final PasswordResetService passwordResetService;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange:email-exchange}")
    private String emailExchange;
    @Value("${app.rabbitmq.routing-key:email.send}")
    private String emailRoutingKey;

    @PostMapping("/request")
    public ResponseEntity<?> requestReset(@RequestParam String email) {
        // Primeiro tenta encontrar como Paciente
        Paciente paciente = pacienteRepository.findByEmail(email).orElse(null);
        if (paciente != null) {
            PasswordResetToken token = passwordResetService.createToken(email, "PACIENTE");
            sendResetEmail(email, paciente.getNome(), token.getCodigo());
            return ResponseEntity.ok("Código de verificação enviado para seu e-mail");
        }
        
        // Se não encontrou como Paciente, tenta como Psicólogo
        Psicologo psicologo = psicologoRepository.findByEmail(email).orElse(null);
        if (psicologo != null) {
            PasswordResetToken token = passwordResetService.createToken(email, "PSICOLOGO");
            sendResetEmail(email, psicologo.getNome(), token.getCodigo());
            return ResponseEntity.ok("Código de verificação enviado para seu e-mail");
        }
        
        return ResponseEntity.badRequest().body("Email não encontrado");
    }
    
    private void sendResetEmail(String email, String nomeUsuario, String codigo) {
        String subject = "Código de redefinição de senha - PsiRizerio";
        String body = String.format(
            "Olá, %s!\n\n" +
            "Você solicitou a redefinição de sua senha no PsiRizerio.\n\n" +
            "Seu código de verificação é: %s\n\n" +
            "Este código expira em 15 minutos.\n\n" +
            "Se você não solicitou esta redefinição, ignore este e-mail.\n\n" +
            "Atenciosamente,\n" +
            "Equipe PsiRizerio", 
            nomeUsuario, 
            codigo
        );
        Map<String, Object> payload = Map.of(
                "to", email,
                "subject", subject,
                "body", body
        );
        rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, payload);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmReset(@RequestParam String codigo, @RequestParam String novaSenha) {
        var resetTokenOpt = passwordResetService.validateCode(codigo);
        if (resetTokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Código inválido ou expirado");
        }
        
        PasswordResetToken resetToken = resetTokenOpt.get();
        String email = resetToken.getEmail();
        String tipoUsuario = resetToken.getTipoUsuario();
        
        // Atualiza a senha baseado no tipo de usuário
        if ("PACIENTE".equals(tipoUsuario)) {
            Paciente paciente = pacienteRepository.findByEmail(email).orElse(null);
            if (paciente != null) {
                paciente.setSenha(passwordEncoder.encode(novaSenha));
                pacienteRepository.save(paciente);
            }
        } else if ("PSICOLOGO".equals(tipoUsuario)) {
            Psicologo psicologo = psicologoRepository.findByEmail(email).orElse(null);
            if (psicologo != null) {
                psicologo.setSenha(passwordEncoder.encode(novaSenha));
                psicologoRepository.save(psicologo);
            }
        }
        
        passwordResetService.consumeToken(resetToken);
        return ResponseEntity.ok("Senha redefinida com sucesso");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateResetCode(@RequestParam String codigo) {
        var resetTokenOpt = passwordResetService.validateCode(codigo);
        if (resetTokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Código inválido ou expirado");
        }
        
        PasswordResetToken resetToken = resetTokenOpt.get();
        return ResponseEntity.ok(Map.of(
            "valid", true,
            "message", "Código válido",
            "email", resetToken.getEmail(),
            "expiryTime", resetToken.getExpiryDate()
        ));
    }
}

