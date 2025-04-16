package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.PreferenciaMapper;
import br.com.backend.PsiRizerio.persistence.entities.Preferencia;
import br.com.backend.PsiRizerio.persistence.repositories.PreferenciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PreferenciaService {

    private final PreferenciaRepository preferenciaRepository;
    private final PreferenciaMapper preferenciaMapper;

    public PreferenciaService(PreferenciaRepository preferenciaRepository, PreferenciaMapper preferenciaMapper) {
        this.preferenciaRepository = preferenciaRepository;
        this.preferenciaMapper = preferenciaMapper;
    }

    public Preferencia createPreferencia(Preferencia preferencia) {
        if (preferenciaRepository.existsByHorarioAndDiaSemana(preferencia.getHorario(), preferencia.getDiaSemana())) {
            throw new EntidadeConflitoException();
        }

        preferencia.setCreatedAt(LocalDateTime.now());
        return preferenciaRepository.save(preferencia);
    }

    public Preferencia updatePreferencia(Integer id, Preferencia preferencia) {
        Preferencia preferenciaToUpdate = preferenciaRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        if (preferenciaRepository.existsByHorarioAndDiaSemanaAndIdNot(
                preferencia.getHorario(), preferencia.getDiaSemana(), id)) {
            throw new EntidadeConflitoException();
        }

        preferenciaToUpdate.setUpdatedAt(LocalDateTime.now());

        return preferenciaRepository.save(preferencia);
    }

    public Preferencia findPreferenciaById(Integer id) {
        return preferenciaRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);
    }

    public void deletePreferencia(Integer id) {
        Preferencia preferencia = preferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preferência não encontrada"));
        preferenciaRepository.delete(preferencia);
    }

    public List<Preferencia> findAllPreferencias() {
        if (preferenciaRepository.findAll().isEmpty()) {
            throw new EntidadeSemConteudoException();
        }
        return preferenciaRepository.findAll();
    }
}
