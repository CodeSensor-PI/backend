package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Paciente save(Paciente paciente);

    void deleteById(Integer id);
    Optional<Paciente> findById(Integer id);
    Optional<Paciente> findByEmail(String email);
    Optional<Paciente> findByCpf(String cpf);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Integer id);

    boolean existsByCpfIgnoreCaseAndIdNot(String cpf, Integer id);

    boolean existsByCpf(String cpf);

}
