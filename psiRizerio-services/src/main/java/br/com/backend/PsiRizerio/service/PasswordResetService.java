package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.PasswordResetToken;
import br.com.backend.PsiRizerio.persistence.repositories.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetToken createToken(Paciente paciente) {
        tokenRepository.deleteByPaciente(paciente);
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusHours(1);
        PasswordResetToken resetToken = new PasswordResetToken(token, paciente, expiry);
        return tokenRepository.save(resetToken);
    }

    public Optional<PasswordResetToken> validateToken(String token) {
        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByToken(token);
        if (resetTokenOpt.isPresent()) {
            PasswordResetToken resetToken = resetTokenOpt.get();
            if (resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                return Optional.of(resetToken);
            }
        }
        return Optional.empty();
    }

    public void consumeToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }
}

