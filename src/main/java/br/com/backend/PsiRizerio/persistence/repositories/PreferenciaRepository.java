package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.enums.DiaSemana;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Integer> {
    boolean existsByHorarioAndDiaSemana(String horario, DiaSemana diaSemana);

    boolean existsByHorarioAndDiaSemanaAndIdNot(String horario, DiaSemana diaSemana, Integer id);

    Optional<Preferencia> findFirstByFkPaciente_Id(Integer fkPacienteId);

}
