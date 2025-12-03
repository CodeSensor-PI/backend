package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.MuitasRequisicoesException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationRateLimiterService {

    @Autowired
    private StringRedisTemplate redis;

    private static final int MAX_TENTATIVAS = 5;
    private static final long BLOQUEIO_SEGUNDOS = 60;

    public String buildKey(String email, String tipoUsuario) {
        return "login:tentativas:" + tipoUsuario + ":" + DigestUtils.sha256Hex(email);
    }


    public int getTentativas(String key) {
        String valor = redis.opsForValue().get(key);
        return valor != null ? Integer.parseInt(valor) : 0;
    }

    public void validarTentativasOuErro(String key, int tentativas) {
        if (tentativas >= MAX_TENTATIVAS) {
            Long ttl = redis.getExpire(key);
            throw new MuitasRequisicoesException(
                    "Muitas tentativas. Aguarde " + ttl + " segundos.",
                    ttl
            );
        }
    }

    public void registrarFalha(String key, int tentativas) {
        int novasTentativas = tentativas + 1;
        redis.opsForValue().set(
                key,
                String.valueOf(novasTentativas),
                BLOQUEIO_SEGUNDOS,
                TimeUnit.SECONDS
        );

        if (novasTentativas >= MAX_TENTATIVAS) {
            throw new MuitasRequisicoesException(
                    "Conta bloqueada por " + BLOQUEIO_SEGUNDOS + " segundos.",
                    BLOQUEIO_SEGUNDOS
            );
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Credenciais inválidas. Tentativas restantes: " + (MAX_TENTATIVAS - novasTentativas)
        );
    }

    public void registrarSucesso(String key) {
        redis.delete(key);
    }
}
