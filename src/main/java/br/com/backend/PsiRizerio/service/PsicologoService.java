package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.psicologoDTO.PsicologoTokenDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadePrecondicaoFalhaException;
import br.com.backend.PsiRizerio.mapper.PsicologoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import br.com.backend.PsiRizerio.persistence.repositories.PsicologoRepository;
import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PsicologoService {

    private final PsicologoRepository psicologoRepository;
    private final PsicologoMapper psicologoMapper;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;

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

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                psicologo.getEmail(), psicologo.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Psicologo usuarioAutenticado =
                psicologoRepository.findByEmailIgnoreCase(psicologo.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return psicologoMapper.toDtoToken(usuarioAutenticado, token);
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
