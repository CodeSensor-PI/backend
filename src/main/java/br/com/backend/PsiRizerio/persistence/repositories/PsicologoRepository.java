package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsicologoRepository extends JpaRepository<Psicologo, Integer> {
}
