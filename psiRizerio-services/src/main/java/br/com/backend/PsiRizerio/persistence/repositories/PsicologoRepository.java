package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PsicologoRepository extends JpaRepository<Psicologo, Integer> {
    boolean existsByEmailOrCrpIgnoreCase(String email, String crp);

    boolean existsByCrpIgnoreCaseAndIdNot(String crp, Integer id);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Integer id);


    Optional<Psicologo> findByEmailIgnoreCase(String email);

    Optional<Psicologo> findByEmail(String username);
}
