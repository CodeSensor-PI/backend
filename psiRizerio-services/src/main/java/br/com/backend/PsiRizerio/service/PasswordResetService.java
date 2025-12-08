package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.persistence.entities.PasswordResetToken;
import br.com.backend.PsiRizerio.persistence.repositories.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetTokenRepository tokenRepository;

    @Transactional
    public PasswordResetToken createToken(String email, String tipoUsuario) {
        tokenRepository.deleteByEmail(email);
        String codigo = generateRandomCode();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15); // Reduzido para 15 minutos para códigos
        PasswordResetToken resetToken = new PasswordResetToken(codigo, email, tipoUsuario, expiry);
        return tokenRepository.save(resetToken);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Gera número entre 100000 e 999999
        return String.valueOf(code);
    }

    public Optional<PasswordResetToken> validateCode(String codigo) {
        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByCodigo(codigo);
        if (resetTokenOpt.isPresent()) {
            PasswordResetToken resetToken = resetTokenOpt.get();
            if (resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                return Optional.of(resetToken);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public void consumeToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }
}

