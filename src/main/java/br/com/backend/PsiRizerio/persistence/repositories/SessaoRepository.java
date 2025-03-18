package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    Sessao save(Sessao sessao);
    List<Sessao> findAll();
    Optional<Sessao> findById(Integer id);
    void deleteById(Integer id);
    Boolean existsByDtHrSessaoAndFkCliente(LocalDateTime dtHrSessao, Integer fkCliente);

    boolean existsByDtHrSessao(LocalDateTime dtHrSessao);

    boolean existsByDtHrSessaoBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByDtHrSessaoBetweenAndIdNot(LocalDateTime start, LocalDateTime end, Integer id);
}
