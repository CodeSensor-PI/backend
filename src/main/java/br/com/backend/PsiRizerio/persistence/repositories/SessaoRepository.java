package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Sessao;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    Sessao save(Sessao sessao);
    void deleteById(Integer id);

    List<Sessao> findAll();
    Optional<Sessao> findById(Integer id);

    List<Sessao> findByFkCliente(Usuario usuario);

    @Query("SELECT s FROM Sessao s WHERE s.fkCliente = :id")
    List<Sessao> findByFkCliente(Integer id);

    boolean existsByDataAndHora(LocalDate data, LocalTime hora);

    boolean existsByDataAndHoraBetween(LocalDate data, LocalTime hora, LocalTime hora2);

    boolean existsByDataAndHoraBetweenAndIdNot(LocalDate data, LocalTime hora, LocalTime hora2, Integer id);

    List<Sessao> findByDataAndHoraBetween(LocalDate data, LocalTime hora, LocalTime hora2);

}
