package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    Sessao save(Sessao sessao);
    void deleteById(Integer id);

    List<Sessao> findAll();
    Optional<Sessao> findById(Integer id);
}
