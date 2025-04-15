package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {

    @Query("SELECT t FROM Telefone t WHERE t.fkCliente.id = :id")
    List<Telefone> findByFkCliente(Integer id);
}
