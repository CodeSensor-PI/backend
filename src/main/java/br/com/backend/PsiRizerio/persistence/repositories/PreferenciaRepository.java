package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.enums.DiaSemana;
import br.com.backend.PsiRizerio.persistence.entities.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Integer> {
    boolean existsByHorarioAndDiaSemana(String horario, DiaSemana diaSemana);

    boolean existsByHorarioAndDiaSemanaAndIdNot(String horario, DiaSemana diaSemana, Integer id);
}
