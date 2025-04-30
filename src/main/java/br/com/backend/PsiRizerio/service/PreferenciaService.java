package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.PreferenciaMapper;
import br.com.backend.PsiRizerio.persistence.entities.Preferencia;
import br.com.backend.PsiRizerio.persistence.repositories.PreferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferenciaService {

    private final PreferenciaRepository preferenciaRepository;
    private final PreferenciaMapper preferenciaMapper;

    public Preferencia createPreferencia(Preferencia preferencia) {
        if (preferenciaRepository.existsByHorarioAndDiaSemana(preferencia.getHorario(), preferencia.getDiaSemana())) {
            throw new EntidadeConflitoException();
        }

        preferencia.setCreatedAt(LocalDateTime.now());
        return preferenciaRepository.save(preferencia);
    }

    public Preferencia updatePreferencia(Integer id, Preferencia preferencia) {
        // Busca a entidade existente no banco
        Preferencia preferenciaToUpdate = preferenciaRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        // Verifica se já existe um conflito de horário e dia da semana
        if (preferenciaRepository.existsByHorarioAndDiaSemanaAndIdNot(
                preferencia.getHorario(), preferencia.getDiaSemana(), id)) {
            throw new EntidadeConflitoException();
        }

        // Atualiza os campos necessários
        preferenciaToUpdate.setHorario(preferencia.getHorario());
        preferenciaToUpdate.setDiaSemana(preferencia.getDiaSemana());
        preferenciaToUpdate.setUpdatedAt(LocalDateTime.now());

        // Atualiza o fkPaciente, se necessário
        if (preferencia.getFkPaciente() != null) {
            preferenciaToUpdate.setFkPaciente(preferencia.getFkPaciente());
        }

        // Salva a entidade atualizada
        return preferenciaRepository.save(preferenciaToUpdate);
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
