package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User user);
    void deleteById(Integer id);

    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);

    boolean existsByEmailOrCpfIgnoreCase(String email, String cpf);

    boolean existsByEmailOrCpfAndIdNot(String email, String cpf, Integer id);

    boolean existsByCpfIgnoreCaseAndIdNot(String cpf, Integer id);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Integer id);
}
