package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.repositories.SessaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final SessaoRepository sessaoRepository;

    public SessaoService(SessaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    public Sessao save(Sessao sessao) {
        if (sessao.getDtHrSessao() == null || sessao.getStatusSessao() == null || sessao.getAnotacao() == null
                || sessao.getTipo() == null || sessao.getFkCliente() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos inválida");
        }

        if (sessao == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consulta inválida");
        }

        if (sessaoRepository.existsByDtHrSessao(sessao.getDtHrSessao())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Consulta já cadastrada");
        }

        LocalDateTime start = sessao.getDtHrSessao().minusHours(1);
        LocalDateTime end = sessao.getDtHrSessao().plusHours(1);

        if (sessaoRepository.existsByDtHrSessaoBetweenAndIdNot(start, end, sessao.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma consulta agendada dentro de 1 hora deste horário");
        }


        try {
            sessao.setCreatedAt(LocalDateTime.now());
            sessao.setUpdatedAt(sessao.getCreatedAt());
            return sessaoRepository.save(sessao);
        } catch (Exception e) {
            log.error("Erro ao salvar consulta: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar consulta");
        }
    }

    public Sessao update(Integer id, Sessao sessao) {

        Sessao sessaoToUpdate = sessaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão não encontrada"));

        if (sessaoRepository.existsByDtHrSessao(sessao.getDtHrSessao())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Consulta já cadastrada");
        }

        LocalDateTime start = sessao.getDtHrSessao().minusHours(1);
        LocalDateTime end = sessao.getDtHrSessao().plusHours(1);

        if (sessaoRepository.existsByDtHrSessaoBetween(start, end)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma consulta agendada dentro de 1 hora deste horário");
        }

        try {
            sessaoToUpdate.setDtHrSessao(sessao.getDtHrSessao());
            sessaoToUpdate.setStatusSessao(sessao.getStatusSessao());
            sessaoToUpdate.setAnotacao(sessao.getAnotacao());
            sessaoToUpdate.setTipo(sessao.getTipo());
            sessaoToUpdate.setFkCliente(sessao.getFkCliente());
            sessaoToUpdate.setUpdatedAt(LocalDateTime.now());

            return sessaoRepository.save(sessaoToUpdate);
        } catch (Exception e) {
            log.error("Erro ao atualizar consulta: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar consulta");
        }
    }

    public List<Sessao> findAll() {
        if (sessaoRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Consultas não encontradas");
        }

        try {
            return sessaoRepository.findAll();
        } catch (Exception e) {
            log.error("Erro ao buscar consultas: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar consultas");
        }
    }

    public Sessao findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        Sessao sessao = sessaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada"));

        try {
            return sessao;
        } catch (Exception e) {
            log.error("Consulta não encontrada: {}", e.getMessage(), e);
            throw new RuntimeException("Consulta não encontrada");
        }

    }

    public void cancelSchedule(Integer id) {
        if (!sessaoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada");
        }

        try {
            sessaoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cancelar consulta");
        }
    }
}
