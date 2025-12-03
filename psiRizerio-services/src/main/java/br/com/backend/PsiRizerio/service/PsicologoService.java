package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.psicologoDTO.PsicologoTokenDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import br.com.backend.PsiRizerio.exception.*;
import br.com.backend.PsiRizerio.mapper.PsicologoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import br.com.backend.PsiRizerio.persistence.repositories.PsicologoRepository;
import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PsicologoService {

    private final PsicologoRepository psicologoRepository;
    private final PsicologoMapper psicologoMapper;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final StringRedisTemplate redis;

    private static final int MAX_TENTATIVAS = 5;
    private static final long BLOQUEIO_SEGUNDOS = 60;

    public Psicologo createPsicologo(Psicologo psicologo) {
        if (psicologoRepository.existsByEmailOrCrpIgnoreCase(psicologo.getEmail(), psicologo.getCrp())
                && psicologo.getStatus() == StatusUsuario.ATIVO) throw new EntidadeConflitoException();

        if (!isValidEmail(psicologo.getEmail())) throw new EntidadeInvalidaException();

        String senhaCriptografada = passwordEncoder.encode(psicologo.getSenha());
        psicologo.setSenha(senhaCriptografada);

        psicologo.setCreatedAt(LocalDateTime.now());
        return psicologoRepository.save(psicologo);
    }

    public Psicologo update(Integer id, Psicologo psicologo) {
        Psicologo psicologoToUpdate = psicologoRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        if (psicologoRepository.existsByCrpIgnoreCaseAndIdNot(psicologo.getCrp(), id) ||
                psicologoRepository.existsByEmailIgnoreCaseAndIdNot(psicologo.getEmail(), id)) {
            throw new EntidadeConflitoException();
        }

        if (!isValidEmail(psicologo.getEmail())) throw new EntidadeInvalidaException();

        psicologoToUpdate.setNome(psicologo.getNome());
        psicologoToUpdate.setCrp(psicologo.getCrp());
        psicologoToUpdate.setEmail(psicologo.getEmail());
        psicologoToUpdate.setTelefone(psicologo.getTelefone());
        psicologoToUpdate.setFkRoles(psicologo.getFkRoles());
        psicologoToUpdate.setUpdatedAt(LocalDateTime.now());

        return psicologoRepository.save(psicologoToUpdate);
    }

    public Psicologo findById(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        Psicologo psicologo = psicologoRepository.findById(id).orElseThrow((EntidadeNaoEncontradaException::new));

        return psicologo;
    }

    public Psicologo desativarPsicologo(Integer id) {
        Psicologo psicologo = psicologoRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        psicologo.setStatus(StatusUsuario.INATIVO);
        psicologo.setUpdatedAt(LocalDateTime.now());
        return psicologoRepository.save(psicologo);
    }

    public Psicologo ativarPsicologo(Integer id) {
        Psicologo psicologo = psicologoRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        psicologo.setStatus(StatusUsuario.ATIVO);
        psicologo.setUpdatedAt(LocalDateTime.now());

        return psicologoRepository.save(psicologo);
    }

    public List<Psicologo> findAll() {
        List<Psicologo> psicologos = psicologoRepository.findAll();
        if (psicologos.isEmpty()) throw new EntidadeNaoEncontradaException();
        return psicologos;
    }

    public PsicologoTokenDTO autenticar(Psicologo psicologo) {

        final String email = psicologo.getEmail();
        final String key = "login:tentativas:" + email;

        String valor = redis.opsForValue().get(key);
        int tentativas = valor != null ? Integer.parseInt(valor) : 0;

        if (tentativas >= MAX_TENTATIVAS) {
            Long timeToLiveRestante = redis.getExpire(key);
            throw new MuitasRequisicoesException(
                    "Muitas tentativas. Aguarde " + timeToLiveRestante + " segundos.",
                    timeToLiveRestante
            );
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, psicologo.getSenha())
            );

            redis.delete(key);

            Psicologo psicologoAutenticado = psicologoRepository
                    .findByEmailIgnoreCase(email)
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Email do usuário não cadastrado")
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = gerenciadorTokenJwt.generateToken(authentication);

            return psicologoMapper.toDtoToken(psicologoAutenticado, token);

        } catch (Exception e) {
            redis.opsForValue().set(
                    key,
                    String.valueOf(tentativas + 1),
                    BLOQUEIO_SEGUNDOS,
                    TimeUnit.SECONDS
            );

            int restantes = MAX_TENTATIVAS - (tentativas + 1);

            if (restantes <= 0) {
                throw new MuitasRequisicoesException(
                        "Conta bloqueada por " + BLOQUEIO_SEGUNDOS + " segundos.",
                        (long) BLOQUEIO_SEGUNDOS
                );
            }

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciais inválidas. Tentativas restantes: " + restantes
            );
        }
    }


    public void updateSenha(Integer id, String senhaAtual, String novaSenha) {
        Psicologo psicologo = psicologoRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        if (!passwordEncoder.matches(senhaAtual, psicologo.getSenha())) {
            throw new EntidadePrecondicaoFalhaException();
        }

        if (senhaAtual.equals(novaSenha)) {
            throw new EntidadeConflitoException();
        }

        String novaSenhaCripto = passwordEncoder.encode(novaSenha);

        psicologo.setSenha(novaSenhaCripto);
        psicologoRepository.save(psicologo);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
