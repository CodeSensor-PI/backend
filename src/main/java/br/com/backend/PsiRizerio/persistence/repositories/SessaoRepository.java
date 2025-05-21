package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.enums.StatusSessao;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    Sessao save(Sessao sessao);
    void deleteById(Integer id);

    List<Sessao> findAll();
    Optional<Sessao> findById(Integer id);

    List<Sessao> findByFkPaciente(Paciente paciente);

    @Query("SELECT s FROM Sessao s WHERE s.fkPaciente = :id")
    List<Sessao> findByFkPaciente(Integer id);

    List<Sessao> findByDataAndHoraBetween(LocalDate data, LocalTime hora, LocalTime hora2);

    boolean existsByDataAndHoraBetweenAndIdNot(LocalDate data, LocalTime hora, LocalTime localTime, Integer id);

    boolean existsByDataAndHoraBetween(LocalDate data, LocalTime hora, LocalTime localTime);

    boolean existsByDataAndHora(LocalDate data, LocalTime hora);

    List<Sessao> findByStatusSessao(StatusSessao statusSessao);


    List<Sessao> data(LocalDate data);

    List<LocalDate> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
}
